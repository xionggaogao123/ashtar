package com.ashstr.spider.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xyn on 2017/9/26.
 */
@Slf4j
public class HttpUtil {

    private static final CloseableHttpClient httpClient;

    public static final String CHARSET = "UTF-8";

    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    /**
     * @param url      请求URL
     * @param paramMap header参数
     * @return 返回数据
     */
    public static String doGetRequest(String url, Map<String, String> paramMap) {
        try {
            Request request = Request.Get(url);
            request.connectTimeout(1000);
            request.connectTimeout(3000);
            if (paramMap != null) {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }
            Response response = request.execute();
            Content content = response.returnContent();
            return content.asString();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 发送 get 请求
     *
     * @param url    请求url
     * @param params 请求参数
     * @return 响应内容
     */
    public static String doGet(String url, Map<String, String> params) {

        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, "utf-8"));
            }
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            log.error("fail send get request with url={}, params={}, cause={}", url, params, Throwables.getStackTraceAsString(e));
        }
        return null;
    }


    /**
     * @param url      请求URL
     * @param paramMap header参数
     * @return 返回数据
     */
    public static String doPostRequest(String url, Map<String, String> paramMap) {
        try {
            Request request = Request.Post(url);
            request.connectTimeout(1000);
            request.connectTimeout(3000);
            if (paramMap != null) {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }
            Response response = request.execute();
            Content content = response.returnContent();
            return content.asString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * post请求
     *
     * @param url
     * @param json
     * @return
     */
    public static JSONObject doPost(String url, JSONObject json) {
        try {
            StringEntity entity = new StringEntity(json.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            Content content = Request.Post(url).body(entity).execute().returnContent();
            String jsonData = content.asString();
            return JSONObject.parseObject(jsonData);

        } catch (Exception e) {
            return new JSONObject();
        }
    }


    public static String sendPost(String url, Map<String, String> map, String encoding) throws Exception {
        String body = "";

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        List<NameValuePair> nvps = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(),  entry.getValue()));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
        System.out.println("请求地址：" + url);
        System.out.println("请求参数：" + nvps.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpPost.setHeader("Upgrade-Insecure-Requests", "1");
        httpPost.setHeader("Origin", "http://www.huangye88.com");
        httpPost.setHeader("Referer", "http://www.huangye88.com/search.html?kw=%E6%88%BF%E5%9C%B0%E4%BA%A7&type=company");
        httpPost.setHeader("Host", "www.huangye88.com");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36)");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }


    /**
     * 获取cookie
     */
    public static Map<String, List<String>> getGetWithCookieResponseHeader(String urlStr, String cookie) throws IOException {
        URL url = new URL(urlStr);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("cookie", cookie);
        return conn.getHeaderFields();
    }

    /**
     * 将请求参数Map妆化为字符串
     * 如Action:del,id:123存入map 解析为 "Action=del&id=123"
     *
     * @param paramMap
     * @return
     */
    public static String getContentByRequestParams(Map<String, String> paramMap) {
        if (CollectionUtils.isEmpty(paramMap)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder("");
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            stringBuilder = stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }

    /**
     * 解析出get url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     */
    public static Map<String, String> getGetRequestParam(String urlStr) {
        String paramStr = urlStr.split("\\?")[1];
        String[] splitParamStrArray = paramStr.split("&");
        Map<String, String> requestParamMap = new HashMap<>();
        for (String str : splitParamStrArray) {
            String[] param = str.split("=");
            requestParamMap.put(param[0], param[1]);
        }
        return requestParamMap;
    }
}