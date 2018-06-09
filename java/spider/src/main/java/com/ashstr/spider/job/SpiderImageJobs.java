package com.ashstr.spider.job;

import com.ashstr.spider.logic.TuiaSpiderLogic;
import com.ashstr.spider.service.SpiderImageService;
import com.ashstr.spider.util.DateUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @author keven
 * @date 2017-12-17 下午2:18
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("/public/spider")
public class SpiderImageJobs {

    @Autowired
    private TuiaSpiderLogic tuiaSpiderLogic;

    @Autowired
    private SpiderImageService spiderImageService;

    @Scheduled(cron = "0 0/5 * * * ?")
    public String beginSpider() {
        tuiaSpiderLogic.beginSpider();
        return "success";
    }

    @Scheduled(cron = "0 5 0/1 * * ?")
    public void fetchBatchImage() {
        Date now = new Date();
        Date beginTime = DateUtils.getPreHourBeginTime(now);
        Date endTime = DateUtils.getHourBeginTime(now);
        spiderImageService.fetchBatch(getParam(beginTime, endTime));
    }

    private Map<String, Object> getParam(Date beginTime, Date endTime) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("beginTime", beginTime);
        param.put("endTime", endTime);
        return param;
    }

}
