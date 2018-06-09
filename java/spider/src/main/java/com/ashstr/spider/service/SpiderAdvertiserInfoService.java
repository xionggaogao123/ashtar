package com.ashstr.spider.service;


import com.ashstr.spider.dao.SpiderAdvertiserInfoDao;
import com.ashstr.spider.domain.SpiderAdvertiserInfo;
import com.ashstr.spider.domain.TuiaSpiderInfo;
import com.ashstr.spider.util.Response;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author keven
 * @date 2017-12-15 下午1:57
 * @Description
 */
@Slf4j
@Service
public class SpiderAdvertiserInfoService {

    private final SpiderAdvertiserInfoDao spiderAdvertiserInfoDao;

    @Autowired
    public SpiderAdvertiserInfoService(SpiderAdvertiserInfoDao spiderAdvertiserInfoDao) {
        this.spiderAdvertiserInfoDao = spiderAdvertiserInfoDao;
    }

    public void insertSpiderAdvertiserInfo(SpiderAdvertiserInfo spiderAdvertiserInfo) {
        try {
            spiderAdvertiserInfoDao.insert(spiderAdvertiserInfo);
        } catch (Exception e) {
            log.error("insert spiderAdvertiserInfo fail with spiderAdvertiserInfo={}, cause={}",
                    spiderAdvertiserInfo, Throwables.getStackTraceAsString(e));
            Response.fail("insert.spiderAdvertiserInfo");
        }
    }

    public Response<List<TuiaSpiderInfo>> getAdvertiserInfo(Map<String, Object> param) {
        try {
            return Response.ok(spiderAdvertiserInfoDao.getAdvertiserInfo(param));
        }catch (Exception e) {
            log.error("fail getAdvertiserInfo with param={}, cause={}", param, Throwables.getStackTraceAsString(e));
            return Response.fail("get.advertiserInfo.fail");
        }
    }
}
