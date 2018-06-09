package com.ashstr.spider.logic;


import com.ashstr.common.util.ExcelUtil;
import com.ashstr.spider.domain.TuiaSpiderInfo;
import com.ashstr.spider.service.MailService;
import com.ashstr.spider.service.SpiderAdvertiserInfoService;
import com.ashstr.spider.service.SpiderGameAdvertiserRelService;
import com.ashstr.spider.service.SpiderGameInfoService;
import com.ashstr.spider.util.DateUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * @author keven
 * @date 2017-12-17 下午5:05
 * @Description
 */
@Slf4j
@Service
public class TuiaDataExcelLogic {

    @Autowired
    private MailService mailService;

    @Autowired
    private SpiderGameInfoService spiderGameInfoService;

    @Autowired
    private SpiderGameAdvertiserRelService spiderGameAdvertiserRelService;

    @Autowired
    private SpiderAdvertiserInfoService spiderAdvertiserInfoService;


    /**
     * 推啊每日Excel发送人员
     */
    private static String[] mailAcceptManArray;

    private TuiaDataExcelLogic() {
        mailAcceptManArray = new String[4];
        mailAcceptManArray[0] = "dingkun@adbaitai.com";
        mailAcceptManArray[1] = "chenmengxing@adbaitai.com";
        mailAcceptManArray[2] = "likena@adbaitai.com";
        mailAcceptManArray[3] = "xuyin@adbaitai.com";
    }

    public void sendExcelToOP() {
        List<TuiaSpiderInfo> result = getTuiaDataByTime(DateUtils.getYesterdayStartTime(), DateUtils.getYesterdayEndTime());
        HSSFWorkbook hssfWorkbook = ExcelUtil.createExcel(null, result, getTuiaSpiderColumnMap(), "TUIA拉取数据");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] excelByteArray;
        try {
            hssfWorkbook.write(os);
            os.flush();
            excelByteArray = os.toByteArray();
            os.close();
        } catch (Exception e) {
            excelByteArray = new byte[0];
        }

        String fileName = DateUtils.formatB(DateUtils.getYesterdayStartTime()) + "TUIA拉取数据";
        mailService.sendAttachmentsMail(mailAcceptManArray, fileName, "每日推啊数据", fileName + ".xls", new ByteArrayResource(excelByteArray));
    }


    private List<TuiaSpiderInfo> getTuiaDataByTime(Date beginTime, Date endTime) {


        //获取游戏信息(已按game_id分组)
        List<TuiaSpiderInfo> gameInfoNotJoinList = spiderGameInfoService.getGameInfoNotJoin(getParam(beginTime, endTime)).getResult();
        //获取关系数据
        List<TuiaSpiderInfo> relNotJoinList = spiderGameAdvertiserRelService.getRelNotJoin(getParam(beginTime, endTime)).getResult();
        //获取广告主信息
        List<TuiaSpiderInfo> advertiserInfoList = spiderAdvertiserInfoService.getAdvertiserInfo(getParam(beginTime, endTime)).getResult();

        //游戏信息 从List转化为Map，key为game_id
        Map<Long, TuiaSpiderInfo> gameInfoNotJoinMap = new HashMap<>();
        for (TuiaSpiderInfo tuiaSpiderInfo : gameInfoNotJoinList) {
            gameInfoNotJoinMap.put(tuiaSpiderInfo.getGameId(), tuiaSpiderInfo);
        }
        //关系数据转为Map,key为广告主id
        Map<Long, TuiaSpiderInfo> relInfoMap = new HashMap<>();
        for (TuiaSpiderInfo tuiaSpiderInfo : relNotJoinList) {
            if (tuiaSpiderInfo.getAdvertiserInfoId() != null) {
                relInfoMap.put(tuiaSpiderInfo.getAdvertiserInfoId(), tuiaSpiderInfo);
            }
        }
        //广告主信息转为Map，key为广告主id
        Map<Long, TuiaSpiderInfo> advertiserInfoMap = new HashMap<>();
        for (TuiaSpiderInfo tuiaSpiderInfoDO : advertiserInfoList) {
            if (tuiaSpiderInfoDO.getAdvertiserInfoId() != null) {
                advertiserInfoMap.put(tuiaSpiderInfoDO.getAdvertiserInfoId(), tuiaSpiderInfoDO);
            }
        }
        //以关系数据为主，设置关系数据中的游戏数据信息
        for (TuiaSpiderInfo tuiaSpiderInfoDO : relNotJoinList) {
            TuiaSpiderInfo gameInfoDO = gameInfoNotJoinMap.get(tuiaSpiderInfoDO.getGameId());
            tuiaSpiderInfoDO.setGameLink(gameInfoDO.getGameLink());
            tuiaSpiderInfoDO.setImageUrl(gameInfoDO.getImageUrl());
            tuiaSpiderInfoDO.setGameType(gameInfoDO.getGameType());
        }

        //以广告主信息为主，将关系信息中的数据填入广告主信息
        List<TuiaSpiderInfo> tuiaSpiderInfoDOList = new ArrayList<>();
        for (Map.Entry<Long, TuiaSpiderInfo> entry : advertiserInfoMap.entrySet()) {
            TuiaSpiderInfo tuiaSpiderInfoDO = relInfoMap.get(entry.getKey());
            TuiaSpiderInfo advertiserInfoDO = entry.getValue();
            advertiserInfoDO.setGameId(tuiaSpiderInfoDO.getGameId());
            advertiserInfoDO.setGameLink(tuiaSpiderInfoDO.getGameLink());
            advertiserInfoDO.setGameType(tuiaSpiderInfoDO.getGameType());
            advertiserInfoMap.put(entry.getKey(), advertiserInfoDO);
            tuiaSpiderInfoDOList.add(advertiserInfoDO);
        }
        return tuiaSpiderInfoDOList;
    }


    private Map<String, String> getTuiaSpiderColumnMap() {
        Map<String, String> columnMap = new LinkedHashMap<>();
        columnMap.put("时间", "createTime");
        columnMap.put("广告位", "slotId");
        columnMap.put("游戏ID", "gameId");
        columnMap.put("游戏链接", "gameLink");
        columnMap.put("图片链接", "imageUrl");
        columnMap.put("游戏类型", "gameType");
        columnMap.put("广告主", "advertiserLink");
        columnMap.put("广告主信息", "advertiserRemark");
        columnMap.put("广告主一级行业", "advertiserParentType");
        columnMap.put("广告主二级行业", "advertiserType");
        columnMap.put("app", "appName");
        columnMap.put("拉取数", "countNum");
        return columnMap;
    }

    private Map<String, Object> getParam(Date beginTime, Date endTime) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("beginTime", beginTime);
        param.put("endTime", endTime);
        return param;
    }


}
