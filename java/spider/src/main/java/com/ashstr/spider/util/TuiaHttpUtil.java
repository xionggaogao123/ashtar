package com.ashstr.spider.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author keven
 * @date 2017-12-15 下午3:20
 * @Description
 */
@Slf4j
public class TuiaHttpUtil {


    /**
     * 第一步：拼接游戏链接302之前的链接
     *
     * @param slotId
     * @param appKey
     * @return
     */
    public static String getSourceUrl(Integer slotId, String appKey) {
        if (StringUtils.isEmpty(appKey) || slotId == null) {
            return "";
        }
        return "https://engine.tuia.cn/index/activity?appKey=" + appKey + "&adslotId=" + slotId;
    }

    /**
     * 第二步：获取sourceUrl 所对应的游戏链接列表
     *
     * @param sourceUrl
     * @return
     */
    public static List<String> getActivityUrlList(String sourceUrl, Map<String, String> cookieMap) {
        if (cookieMap == null) {
            cookieMap = new HashMap<>();
        }
        List<String> activityUrls = new ArrayList<>();
        Map<String, List<String>> headerFieldsMap = getGet302Location(sourceUrl, "");
        if (headerFieldsMap == null || headerFieldsMap.get("Location") == null) {
            log.info("sourceUrl链接跳转失败：" + sourceUrl);
            return activityUrls;
        }
        String moduleLocation = headerFieldsMap.get("Location").get(0);
        try {
            headerFieldsMap = HttpUtil.getGetWithCookieResponseHeader(sourceUrl, "");
            updateCookieMap(cookieMap, headerFieldsMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isMainMeetUrl(moduleLocation)) {
            activityUrls.addAll(getGameUrlsByMainMeetUrl(moduleLocation, cookieMap));
        } else {
            activityUrls.add(moduleLocation);
        }
        return activityUrls;
    }

    public static Map<String, List<String>> getGet302Location(String urlStr, String cookie) {
        HttpURLConnection conn = null;
        Map<String, List<String>> headerFieldsMap = new HashMap<>();
        try {
            URL serverUrl = new URL(urlStr);
            conn = (HttpURLConnection) serverUrl
                    .openConnection();
            conn.setRequestMethod("GET");
            // 必须设置false，否则会自动redirect到Location的地址
            conn.setInstanceFollowRedirects(false);
            conn.addRequestProperty("Accept-Charset", "UTF-8");
            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
            conn.addRequestProperty("Cookie", cookie);
            conn.connect();
            headerFieldsMap = conn.getHeaderFields();
        } catch (Exception e) {
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return headerFieldsMap;
    }

    /**
     * 解析游戏页面，提取关键元素
     *
     * @param activityUrl
     * @param cookieMap
     * @return
     */
    public static Map<String, String> analysisParamByActivityPage(String activityUrl, Map<String, String> cookieMap) {
        Map<String, String> paramMap = new HashMap<>();
        HttpURLConnection httpConn = null;
        try {
            URL url = new URL(activityUrl);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setInstanceFollowRedirects(false);
            httpConn.setRequestMethod("GET");
            httpConn.addRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
            httpConn.addRequestProperty("Accept-Encoding", "gzip, deflate");
            httpConn.addRequestProperty("accept-language", "zh-CN,zh;q=0.8,en;q=0.6");
            httpConn.addRequestProperty("cache-control", "no-cache");
            httpConn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
            httpConn.addRequestProperty("pragma", "no-cache");
            httpConn.addRequestProperty("Cookie", CookieUtil.getCookie(cookieMap));
            httpConn.connect();

            //更新cookies
            Map<String, List<String>> headerFieldsMap = httpConn.getHeaderFields();
            updateCookieMap(cookieMap, headerFieldsMap);

            String pageContent = readStream(httpConn.getInputStream(), httpConn.getContentEncoding());

            String activityType = RegexUtil.match("activityType: '(\\d+)',", pageContent);
            String isShowNew = RegexUtil.match("isShowNew: ([a-z]+)", pageContent);
            String couponVersion1 = RegexUtil.match("CFG.couponVersion = '(\\d+)';", pageContent, 1);
            String couponVersion2 = null;
            try {
                couponVersion2 = RegexUtil.match("CFG.couponVersion = '(\\d+)';", pageContent, 2);
            } catch (Exception e) {
                //ignore
            }
            String tokenKey = RegexUtil.match("var key = '(\\S+)';", pageContent);

            paramMap.put("activityType", activityType);
            paramMap.put("isShowNew", isShowNew);
            if (!StringUtils.isEmpty(isShowNew)) {
                if ("true".equals(isShowNew.trim()) && couponVersion2 != null) {
                    paramMap.put("couponVersion", couponVersion2);
                } else {
                    paramMap.put("couponVersion", couponVersion1);
                }
            }
            paramMap.put("tokenKey", tokenKey);

        } catch (Exception e) {
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return paramMap;
    }

    public static String getToken(Integer slotId, Integer activityId, Integer activityType, String activityUrl, Map<String, String> cookieMap) {
        String promoteUrlDomain = "activity.tuia.cn";
        try {
            URL url = new URL(activityUrl);
            promoteUrlDomain = url.getHost();
        } catch (MalformedURLException e) {
        }

        String doJoinUrl = "http://" + promoteUrlDomain + "/token/getToken";
        HttpURLConnection httpConn = null;
        String token = null;
        // 拼接请求参数
        Map<String, String> paramMap = TuiaHttpUtil.getToken();
        String content = HttpUtil.getContentByRequestParams(paramMap);
        try {
            URL url = new URL(doJoinUrl);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");// 提交模式
            httpConn.addRequestProperty("Accept", "*/*");
            httpConn.addRequestProperty("Accept-Encoding", "gzip, deflate");
            httpConn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
            httpConn.addRequestProperty("Referer", activityUrl);
            httpConn.addRequestProperty("Cookie", CookieUtil.getCookie(cookieMap));
            httpConn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);

            httpConn.connect();

            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpConn.getOutputStream());
            printWriter.write(content);//post的参数 xx=xx&yy=yy
            // flush输出流的缓冲
            printWriter.flush();

            //更新cookies
            Map<String, List<String>> headerFieldsMap = httpConn.getHeaderFields();
            updateCookieMap(cookieMap, headerFieldsMap);

            //读取响应
            String str = readStream(httpConn.getInputStream(), httpConn.getContentEncoding());
            JSONObject returnJson = JSONObject.parseObject(str);
            boolean success = returnJson.getBoolean("success");
            if (success) {
                token = returnJson.getString("token");
            } else {
                log.info("[getToken fail!]content:" + content + ",msg:");
            }
        } catch (Exception e) {
            log.info("[getToken Error!]content:" + content);
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return token;
    }

    //请求node服务器，解析token
    public static String resolveToken(String token, String key) {
        //必须绑定hosts：101.37.77.179    titan.adbaitai.com
        String doJoinUrl = "http://titan.adbaitai.com/lingdinode/";
        try {
            // 拼接请求参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            jsonObject.put("key", key);
            JSONObject returnJson = HttpUtil.doPost(doJoinUrl, jsonObject);
            boolean success = returnJson.getBoolean("success");
            if (success) {
                token = returnJson.getJSONObject("data").getString("token");
            } else {
                token = "";
            }
        } catch (Exception e) {
            log.error("[doJoin Error!]ex:", e);
            log.info("[doJoin Error!]key:" + key);
            token = "";
        }
        return token;

    }


    /**
     * 获取OrderId，用于请求奖品
     *
     * @param slotId
     * @param activityId
     * @param token
     * @param cookieMap
     * @return
     */
    public static String getOrderIdByDoJoin(Integer slotId, Integer activityId, Integer activityType, String refer, String token, Map<String, String> cookieMap) {
        String promoteUrlDomain = "activity.tuia.cn";
        try {
            URL url = new URL(refer);
            promoteUrlDomain = url.getHost();
        } catch (MalformedURLException e) {
        }
        String doJoinUrl = "http://" + promoteUrlDomain + "/activity/doJoin?cType=null";
        HttpURLConnection httpConn = null;
        String orderId = null;
        // 拼接请求参数
        Map<String, String> paramMap = TuiaHttpUtil.getDoJoinRequestParam(slotId, activityType, activityId, token);
        String content = HttpUtil.getContentByRequestParams(paramMap);
        try {
            URL url = new URL(doJoinUrl);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");// 提交模式
            httpConn.addRequestProperty("Accept", "*/*");
            httpConn.addRequestProperty("Accept-Encoding", "gzip, deflate");
            httpConn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
            httpConn.addRequestProperty("Referer", refer);
            httpConn.addRequestProperty("Cookie", CookieUtil.getCookie(cookieMap));
            httpConn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);

            httpConn.connect();

            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpConn.getOutputStream());
            printWriter.write(content);//post的参数 xx=xx&yy=yy
            // flush输出流的缓冲
            printWriter.flush();

            //更新cookies
            Map<String, List<String>> headerFieldsMap = httpConn.getHeaderFields();
            updateCookieMap(cookieMap, headerFieldsMap);

            //读取响应
            String str = readStream(httpConn.getInputStream(), httpConn.getContentEncoding());
            JSONObject returnJson = JSONObject.parseObject(str);
            boolean success = returnJson.getBoolean("success");
            if (success) {
                orderId = returnJson.getJSONObject("data").getString("orderId");
            } else {
                log.info("[getOrderIdByDoJoin fail!]content:" + content + ",msg:");
            }
        } catch (Exception e) {
            log.info("[getOrderIdByDoJoin Error!]content:" + content);
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return orderId;
    }

    /**
     * 模拟抽奖
     *
     * @param orderId
     * @param cookieMap
     * @return
     */
    public static JSONObject getActivityResult(String orderId, String skinId, String showVersion, String refer, Map<String, String> cookieMap) {
        String promoteUrlDomain = "activity.tuia.cn";
        try {
            URL url = new URL(refer);
            promoteUrlDomain = url.getHost();
        } catch (MalformedURLException e) {
        }
        String doResultUrl = "http://" + promoteUrlDomain + "/activity/result";
        Map<String, String> paramMap = getDoResultParam(orderId, skinId, showVersion);
        HttpURLConnection httpConn = null;
        JSONObject returnJson = null;

        // 拼接请求参数
        String content = HttpUtil.getContentByRequestParams(paramMap);
        try {
            URL url = new URL(doResultUrl);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");// 提交模式
            httpConn.addRequestProperty("Accept", "application/json");
            httpConn.addRequestProperty("Accept-Encoding", "gzip, deflate");
            httpConn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.addRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
            httpConn.addRequestProperty("Host", promoteUrlDomain);
            httpConn.addRequestProperty("Origin", "http://" + promoteUrlDomain);
            httpConn.addRequestProperty("X-Requested-With", "XMLHttpRequest");
            httpConn.addRequestProperty("Referer", refer);
            httpConn.addRequestProperty("Cookie", CookieUtil.getCookie(cookieMap));
            httpConn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);

            httpConn.connect();

            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpConn.getOutputStream());
            printWriter.write(content);//post的参数 xx=xx&yy=yy
            // flush输出流的缓冲
            printWriter.flush();

            //更新cookies
            Map<String, List<String>> headerFieldsMap = httpConn.getHeaderFields();
            updateCookieMap(cookieMap, headerFieldsMap);

            //读取响应
            String str = readStream(httpConn.getInputStream(), httpConn.getContentEncoding());
            returnJson = JSONObject.parseObject(str);
            boolean success = returnJson.getBoolean("success");
            if (success) {
                return returnJson.getJSONObject("data");
            } else {
                log.info("[doJoin fail!]content:" + content + ",msg:");
            }
        } catch (Exception e) {
            log.info("[doJoin Error!]content:" + content);
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return returnJson;
    }

    /**
     * 使用gzip进行压缩
     */

    public static String gzip(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return new sun.misc.BASE64Encoder().encode(out.toByteArray());
    }

    /**
     * 获取请求奖品参数
     *
     * @param orderId
     * @return
     */
    private static Map<String, String> getDoResultParam(String orderId, String skinId, String showVersion) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("orderId", orderId);
        requestMap.put("showVersion", showVersion);
        requestMap.put("skinId", skinId);
        requestMap.put("timestamp", Long.toString(new Date().getTime()));
        return requestMap;
    }


    /**
     * 更新cookieMap，通过headerFieldsMap
     *
     * @param cookieMap
     * @param headerFieldsMap
     * @return
     */
    public static Map<String, String> updateCookieMap
    (Map<String, String> cookieMap, Map<String, List<String>> headerFieldsMap) {
        List<String> values = headerFieldsMap.get("Set-Cookie");
        if (!CollectionUtils.isEmpty(values)) {
            for (String value : values) {
                if (value == null) {
                    continue;
                }
                value = value.substring(0, value.indexOf(";"));
                if (!StringUtils.isEmpty(value)) {
                    String[] cookieArray = value.split("=");
                    if (cookieArray.length >= 2) {
                        cookieMap.put(cookieArray[0], cookieArray[1]);
                    }
                }

            }
        }
        return cookieMap;
    }


    /**
     * 从游戏链接中解析出游戏ID
     *
     * @param gameUrl
     * @return
     */

    public static Integer getActivityId(String gameUrl) {
        Map<String, String> paramMap = HttpUtil.getGetRequestParam(gameUrl);
        if (!StringUtils.isEmpty(paramMap.get("id"))) {
            return Integer.parseInt(paramMap.get("id"));
        }
        return null;
    }


    /**
     * 是否为多模块链接
     *
     * @param moduleLocation
     * @return
     */
    public static boolean isMainMeetUrl(String moduleLocation) {
        if (StringUtils.isEmpty(moduleLocation)) {
            return false;
        }
        return moduleLocation.contains("mainMeet");
    }

    /**
     * 通过多模块链接 获取游戏链接列表
     *
     * @param mainMeetUrl
     * @return
     */
    public static List<String> getGameUrlsByMainMeetUrl(String mainMeetUrl, Map<String, String> cookieMap) {
        List<String> gameUrlList = new ArrayList<>();
        HttpURLConnection httpConn = null;
        try {
            URL url = new URL(mainMeetUrl);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setInstanceFollowRedirects(false);
            httpConn.setRequestMethod("GET");
            httpConn.addRequestProperty("Accept", "*/*");
            httpConn.addRequestProperty("Accept-Encoding", "gzip, deflate");
            httpConn.addRequestProperty("accept-language", "zh-CN,zh;q=0.8,en;q=0.6");
            httpConn.addRequestProperty("cache-control", "no-cache");
            httpConn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
            httpConn.addRequestProperty("pragma", "no-cache");
            httpConn.addRequestProperty("Cookie", CookieUtil.getCookie(cookieMap));
            httpConn.connect();

            //更新cookies
            Map<String, List<String>> headerFieldsMap = httpConn.getHeaderFields();
            updateCookieMap(cookieMap, headerFieldsMap);

            //读取响应
            String contentStr = readStream(httpConn.getInputStream(), httpConn.getContentEncoding());
            String mlistString = ((contentStr.split("\"mlist\":\\[\"")[1]).split("\"],\n" + "    \"nlist\""))[0];
            String[] gameUrls = mlistString.split(",");
            for (String gameUrl : gameUrls) {
                gameUrlList.add("http:" + gameUrl.replaceAll("\"", ""));
            }

        } catch (Exception e) {
            log.info("[getGameUrlsByMainMeetUrl fail!]");
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }

        return gameUrlList;
    }

    /**
     * 获取奖品列表
     *
     * @param slotId
     * @param activityId
     * @param cookieMap
     */
    public static boolean getActivityTypeByAjaxOptions(Integer slotId, Integer activityId, String refer, Map<String, String> cookieMap) {
        String promoteUrlDomain = "activity.tuia.cn";
        try {
            URL url = new URL(refer);
            promoteUrlDomain = url.getHost();
        } catch (MalformedURLException e) {
        }
        String ajaxOptionsUrl = "http://" + promoteUrlDomain + "/activity/ajaxOptions";
        HttpURLConnection httpConn = null;
        // 拼接请求参数
        Map<String, String> paramMap = TuiaHttpUtil.getAjaxOptionsRequestParam(slotId, activityId);
        String content = HttpUtil.getContentByRequestParams(paramMap);
        try {
            URL url = new URL(ajaxOptionsUrl);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");// 提交模式
            httpConn.addRequestProperty("Accept", "*/*");
            httpConn.addRequestProperty("Accept-Encoding", "gzip, deflate");
            httpConn.addRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
            httpConn.addRequestProperty("Cookie", CookieUtil.getCookie(cookieMap));
            httpConn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);

            httpConn.connect();

            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpConn.getOutputStream());
            printWriter.write(content);//post的参数 xx=xx&yy=yy
            // flush输出流的缓冲
            printWriter.flush();

            //更新cookies
            Map<String, List<String>> headerFieldsMap = httpConn.getHeaderFields();
            updateCookieMap(cookieMap, headerFieldsMap);

            //读取响应
            String str = readStream(httpConn.getInputStream(), httpConn.getContentEncoding());
            JSONObject returnJson = JSONObject.parseObject(str);
            boolean success = returnJson.getBoolean("success");
            if (success) {
                return true;
            } else {
                log.info("[doJoin fail!]content:" + content + ",msg:");
            }
        } catch (Exception e) {
                log.info("[doJoin Error!]content:" + content);
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return false;
    }

    //获取doJoin参数
    public static Map<String, String> getDoJoinRequestParam(Integer slotId, Integer activityType, Integer activityId, String token) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("activityType", activityType.toString());
        requestMap.put("activityId", activityId.toString());
        requestMap.put("slotId", slotId.toString());
        requestMap.put("token", token);
        requestMap.put("timestamp", Long.toString(System.currentTimeMillis()));
        return requestMap;
    }

    //获取getToken参数
    public static Map<String, String> getToken() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("timestamp", Long.toString(System.currentTimeMillis()));
        return requestMap;
    }

    //获取doJoin参数
    public static Map<String, String> getAjaxOptionsRequestParam(Integer slotId, Integer activityId) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("activityId", activityId.toString());
        requestMap.put("slotId", slotId.toString());
        requestMap.put("timestamp", Long.toString(System.currentTimeMillis()));
        return requestMap;
    }


    private final static String ENCODING = "UTF-8";
    private final static String GZIPCODING = "gzip";

    /**
     * 读取将InputStream中的字节读以字符的形式取到字符串中，如果encoding是gzip，那么需要先有GZIPInputStream进行封装
     *
     * @param inputStream InputStream字节流
     * @param encoding    编码格式
     * @return String类型的形式
     * @throws IOException IO异常
     */
    private static String readStream(InputStream inputStream, String encoding) throws Exception {
        StringBuilder buffer = new StringBuilder();
        ProgressMonitorInputStream pmis = null;

        InputStreamReader inputStreamReader = null;
        GZIPInputStream gZIPInputStream = null;
        if (!StringUtils.isEmpty(encoding) && encoding.contains(GZIPCODING)) {
            gZIPInputStream = new GZIPInputStream(inputStream);
            inputStreamReader = new InputStreamReader(gZIPInputStream, ENCODING);

        } else {
            inputStreamReader = new InputStreamReader(inputStream, ENCODING);
        }

        char[] c = new char[1024];

        int lenI;
        while ((lenI = inputStreamReader.read(c)) != -1) {

            buffer.append(new String(c, 0, lenI));

        }
        inputStream.close();
        if (gZIPInputStream != null) {
            gZIPInputStream.close();
        }
        return buffer.toString();
    }


}
