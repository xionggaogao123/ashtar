package com.ashstr.spider.controller;


import com.alibaba.fastjson.JSONObject;
import com.ashstr.spider.logic.TuiaSpiderLogic;
import com.ashstr.spider.service.SpiderAdvertiserInfoService;
import com.ashstr.spider.service.SpiderImageService;
import com.ashstr.spider.util.DateUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author keven
 * @date 2017-12-15 下午1:56
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("/public/spider")
public class SpiderImageController {

    @Autowired
    private SpiderImageService spiderImageService;

    @Autowired
    private TuiaSpiderLogic tuiaSpiderLogic;

    @Autowired
    private SpiderAdvertiserInfoService spiderAdvertiserInfoService;

    @GetMapping("image/list")
    public String imageList(@RequestParam String date) {
        Date beginTime = DateUtils.string2Date(date);
        Date endTime = DateUtils.string2DateWithHHmmss(date + " 23:59:59");
        //这里我认为返回 List 就好了，但为了兼容，只能这么做了
        List<String> images = spiderImageService.getInTimeSlot(getParam(beginTime, endTime)).getResult();
        return processResult(images);
    }

    private Map<String, Object> getParam(Date beginTime, Date endTime) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("beginTime", beginTime);
        param.put("endTime", endTime);
        return param;
    }

    private String processResult(Object object) {
        return processResultJson(object).toString();
    }

    private JSONObject processResultJson(Object object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        if (object == null) {
            object = new Object();
        }
        jsonObject.put("data", object);
        return jsonObject;
    }

}
