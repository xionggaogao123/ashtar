package com.ashstr.spider.service;


import com.ashstr.spider.dao.SpiderGameAdvertiserRelDao;
import com.ashstr.spider.domain.SpiderGameAdvertiserRel;
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
 * @date 2017-12-15 下午1:59
 * @Description
 */
@Slf4j
@Service
public class SpiderGameAdvertiserRelService {

    private final SpiderGameAdvertiserRelDao spiderGameAdvertiserRelDao;

    @Autowired
    public SpiderGameAdvertiserRelService(SpiderGameAdvertiserRelDao spiderGameAdvertiserRelDao) {
        this.spiderGameAdvertiserRelDao = spiderGameAdvertiserRelDao;
    }

    public void insertAdvertiserRel(SpiderGameAdvertiserRel advertiserRel) {
        try {
            spiderGameAdvertiserRelDao.insert(advertiserRel);
            Response.ok(advertiserRel.getId());
        } catch (Exception e) {
            log.error("fail insert AdvertiserRel with advertiserRel={}, cause={}",
                    advertiserRel, Throwables.getStackTraceAsString(e));
            Response.fail("insert.advertiserRel.fail");
        }
    }

    public Response<List<TuiaSpiderInfo>> getRelNotJoin(Map<String, Object> param) {
        try {
            return Response.ok(spiderGameAdvertiserRelDao.getRelNotJoin(param));
        }catch (Exception e) {
            log.error("fail getGameInfoNotJoin with param={}, cause={}", param, Throwables.getStackTraceAsString(e));
            return Response.fail("");
        }
    }



}
