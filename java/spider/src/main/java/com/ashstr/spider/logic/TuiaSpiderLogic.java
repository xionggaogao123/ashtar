package com.ashstr.spider.logic;


import com.alibaba.fastjson.JSONObject;
import com.ashstr.spider.domain.SpiderAdvertiserInfo;
import com.ashstr.spider.domain.SpiderGameAdvertiserRel;
import com.ashstr.spider.domain.SpiderGameInfo;
import com.ashstr.spider.domain.TuiaApp;
import com.ashstr.spider.service.SpiderAdvertiserInfoService;
import com.ashstr.spider.service.SpiderGameAdvertiserRelService;
import com.ashstr.spider.service.SpiderGameInfoService;
import com.ashstr.spider.util.HttpUtil;
import com.ashstr.spider.util.TuiaHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author keven
 * @date 2017-12-15 下午3:12
 * @Description
 */
@Slf4j
@Service
public class TuiaSpiderLogic {

    private static final List<TuiaApp> appList = new ArrayList<>();

    private static final Random random = new Random();

    private final SpiderGameInfoService spiderGameInfoService;

    private final SpiderAdvertiserInfoService spiderAdvertiserInfoService;

    private final SpiderGameAdvertiserRelService spiderGameAdvertiserRelService;

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    public TuiaSpiderLogic(SpiderGameInfoService spiderGameInfoService,
                           SpiderAdvertiserInfoService spiderAdvertiserInfoService,
                           SpiderGameAdvertiserRelService spiderGameAdvertiserRelService) {
        this.spiderGameInfoService = spiderGameInfoService;
        this.spiderAdvertiserInfoService = spiderAdvertiserInfoService;
        this.spiderGameAdvertiserRelService = spiderGameAdvertiserRelService;
    }


    @PostConstruct
    public void init() {
        appList.add(new TuiaApp("4G8pBVNSCRLnzrnHvAStfTFSq9X9", "Wifi增强器", 262));
        appList.add(new TuiaApp("27gLKFhGpy4jhjB1z6kqn9J1WWvN", "万年历", 412));
        appList.add(new TuiaApp("42VogJE7aPQkvR5QXjRZ4RFQqmU1", "墨迹天气", 68));
        appList.add(new TuiaApp("PMSxibiYZtCQRNGmWx6B5RyAX6u", "车来了", 111));
        appList.add(new TuiaApp("9zyv27QNqpLTnYVeJpvdzGDkEF2", "悦动圈IOS与Android", 1073));
        appList.add(new TuiaApp("3SuptrLZvFmJ6D1PrV5T15Wfu23g", "有信Android", 1462));
        appList.add(new TuiaApp("2DPT42njGNmS6cpDHr85pKB9pTQM", "懒人听说Android", 2406));
        appList.add(new TuiaApp("4TGEnJNqcLK43i9etX6U1GMBj4AB", "猎豹清理大师Android", 1406));
    }


    public void beginSpider() {
        log.info("推啊爬虫开始爬取数据");
        for (final TuiaApp tuiaApp : appList) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        spider(tuiaApp.getAppSlotId(), tuiaApp.getAppKey(), tuiaApp.getAppName());
                    } catch (Exception e) {
                        log.error("TuiaSpider爬取出错。appKey:" + tuiaApp.getAppKey() + ",appName:" + tuiaApp.getAppName());
                        log.error(e.getMessage());
                    }
                }
            });
        }
    }

    private void spider(Integer slotId, String appKey, String appName) {
        //1.拼接原链接
        String sourceUrl = TuiaHttpUtil.getSourceUrl(slotId, appKey);
        if (StringUtils.isEmpty(sourceUrl)) {
            return;
        }

        //2.获取所对应的游戏链接列表
        Map<String, String> cookieMap = new HashMap<>();
        List<String> activityUrls = TuiaHttpUtil.getActivityUrlList(sourceUrl, cookieMap);

        //3.替换设备号
        String deviceId = getDeviceId();
        cookieMap.put("_coll_device", deviceId);
        cookieMap.put("_fmdata", "DEB05CA28D603B1C6EA107D0AAAA6D06B143181D3857437E4B310EB030AE01C305B64E8BEA8C40A11B541720001E6B41ACB5649ADA2D4D7B");
        if (!CollectionUtils.isEmpty(activityUrls)) {
            for (String activityUrl : activityUrls) {
                try {
                    Thread.sleep(random.nextInt(5 * 1000) + 10000);
                    //解析获取游戏ID
                    Integer activityId = TuiaHttpUtil.getActivityId(activityUrl);

                    //获取activityType
                    Integer activityType = 2;
                    String skinId = "010002";
                    String showVersion = "false";
                    String tokenKey = "";
                    Map<String, String> valueMap = TuiaHttpUtil.analysisParamByActivityPage(activityUrl, cookieMap);
                    if (valueMap.get("activityType") != null) {
                        activityType = Integer.parseInt(valueMap.get("activityType"));
                    }
                    if (valueMap.get("couponVersion") != null) {
                        skinId = valueMap.get("couponVersion");
                    }
                    if (valueMap.get("isShowNew") != null) {
                        showVersion = valueMap.get("isShowNew");
                    }
                    if (valueMap.get("tokenKey") != null) {
                        tokenKey = valueMap.get("tokenKey");
                    }

                    //获取奖品
                    Thread.sleep(random.nextInt(3 * 1000) + 3000);
                    //玩3-6次游戏
                    for (int i = 0; i < (random.nextInt(4) + 4); i++) {
                        //获取token
                        String token = TuiaHttpUtil.getToken(slotId, activityId, activityType, activityUrl, cookieMap);
                        token = TuiaHttpUtil.resolveToken(token, tokenKey);
                        //获取orderId
                        String orderId = TuiaHttpUtil.getOrderIdByDoJoin(slotId, activityId, activityType, activityUrl, token, cookieMap);
                        if (!StringUtils.isEmpty(orderId)) {
                            Thread.sleep(random.nextInt(50) + 50);
                            //请求奖品
                            JSONObject returnJson = TuiaHttpUtil.getActivityResult(orderId, skinId, showVersion, activityUrl, cookieMap);
                            handleResult(appName, slotId, activityId, sourceUrl, activityUrl, returnJson);
                        }
                        Thread.sleep(random.nextInt(5 * 1000) + 5000);
                    }
                } catch (Exception e) {
                    //ignore
                    log.error("spider爬取出错。appKey:" + appKey + ",appName:" + appName);
                }
            }
        }
    }

    private void handleResult(String appName, Integer slotId, Integer gameId, String sourceUrl, String gameUrl, JSONObject returnJson) {
        JSONObject reward = returnJson.getJSONObject("lottery");
        String androidDownloadUrl = reward.getString("androidDownloadUrl");
        //图片链接
        String imageUrl = reward.getString("imgurl");
        if (androidDownloadUrl == null || HttpUtil.getGetRequestParam(androidDownloadUrl).get("url") == null) {
            return;
        }
        String[] strArray = HttpUtil.getGetRequestParam(androidDownloadUrl).get("url").split("%3A%2F%2F");
        if (strArray.length < 1) {
            return;
        }
        String haveParamUrl = strArray[1];

        String advertiserUrl = haveParamUrl;
        if (haveParamUrl.contains(".com")) {
            advertiserUrl = haveParamUrl.split(".com")[0] + ".com";
        } else if (haveParamUrl.contains(".cn")) {
            advertiserUrl = haveParamUrl.split(".cn")[0] + ".cn";
        } else if (haveParamUrl.contains(".net")) {
            advertiserUrl = haveParamUrl.split(".net")[0] + ".net";
        }

        /**获取广告主title等信息*/
        String title = reward.getString("title");
        String tip = reward.getString("tip");
        Map<String, String> advertiserMap = new HashMap<>();
        advertiserMap.put("title", title);
        advertiserMap.put("tip", tip);
        String advertiserRemark = JSONObject.toJSONString(advertiserMap);


        /**声明需要保存的数据*/
        SpiderAdvertiserInfo spiderAdvertiserInfo = new SpiderAdvertiserInfo();
        SpiderGameInfo spiderGameInfo = new SpiderGameInfo();
        SpiderGameAdvertiserRel spiderGameAdvertiserRelDO = new SpiderGameAdvertiserRel();
        Date createTime = new Date();

        spiderAdvertiserInfo.setCreateTime(createTime);
        spiderAdvertiserInfo.setSlotId(slotId);
        spiderAdvertiserInfo.setAdvertiserLink(advertiserUrl);
        spiderAdvertiserInfo.setAdvertiserRemark(advertiserRemark);
        spiderAdvertiserInfo.setAppName(appName);
        spiderAdvertiserInfo.setImageUrl(imageUrl);

        spiderGameInfo.setCreateTime(createTime);
        spiderGameInfo.setAgencySourceLink(sourceUrl);
        spiderGameInfo.setGameId(gameId);
        spiderGameInfo.setSlotId(slotId);
        spiderGameInfo.setGameLink(gameUrl);
        spiderGameInfo.setAppName(appName);

        // 这里应该放到 同一个事务
        spiderGameInfoService.insertSpiderGameInfo(spiderGameInfo);
        Long gameInfoId = spiderGameInfo.getId();
        spiderAdvertiserInfoService.insertSpiderAdvertiserInfo(spiderAdvertiserInfo);
        Long advertiserInfoId = spiderAdvertiserInfo.getId();
        spiderGameAdvertiserRelDO.setCreateTime(createTime);
        spiderGameAdvertiserRelDO.setGameId(gameId);
        spiderGameAdvertiserRelDO.setAdvertiserLink(advertiserUrl);
        spiderGameAdvertiserRelDO.setGameInfoId(gameInfoId);
        spiderGameAdvertiserRelDO.setAdvertiserInfoId(advertiserInfoId);
        spiderGameAdvertiserRelService.insertAdvertiserRel(spiderGameAdvertiserRelDO);
    }

    private String getDeviceId() {
        char[] charArray = {'k', 'e', 'v', 'p', 'x', '9', '8', '2', 'b', '0', 'i', 's', '7', 'z', '4', 'o', 'w', '3', 'm', '5', 't', 'l', 'h', 'n', 'g', 'q', 'a', 'j', 'u', 'c', 'f', '1', 'd', 'y', 'r', '6'};
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < 8; i++) {
            stringBuilder = stringBuilder.append(charArray[random.nextInt(charArray.length)]);
        }
        stringBuilder = stringBuilder.append("-");
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 4; i++) {
                stringBuilder = stringBuilder.append(charArray[random.nextInt(charArray.length)]);
            }
            stringBuilder = stringBuilder.append("-");
        }
        for (int i = 0; i < 12; i++) {
            stringBuilder = stringBuilder.append(charArray[random.nextInt(charArray.length)]);
        }
        return stringBuilder.toString();
    }


}
