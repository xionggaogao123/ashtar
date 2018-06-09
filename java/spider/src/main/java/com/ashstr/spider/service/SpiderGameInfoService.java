package com.ashstr.spider.service;


import com.ashstr.spider.dao.SpiderGameInfoDao;
import com.ashstr.spider.domain.SpiderGameInfo;
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
 * @date 2017-12-15 下午2:01
 * @Description
 */
@Slf4j
@Service
public class SpiderGameInfoService {

    private final SpiderGameInfoDao spiderGameInfoDao;

    @Autowired
    public SpiderGameInfoService(SpiderGameInfoDao spiderGameInfoDao) {
        this.spiderGameInfoDao = spiderGameInfoDao;
    }

    public void insertSpiderGameInfo(SpiderGameInfo spiderGameInfo) {
        try {
            spiderGameInfoDao.insert(spiderGameInfo);
            Response.ok(spiderGameInfo.getId());
        } catch (Exception e) {
            log.error("fail insert spiderGameInfo with spiderGameInfo={}, cause={}",
                    spiderGameInfo, Throwables.getStackTraceAsString(e));
            Response.fail("insert.spiderGameInfo.fail");
        }
    }

    public Response<List<TuiaSpiderInfo>> getGameInfoNotJoin(Map<String, Object> param) {
        try {
            return Response.ok(spiderGameInfoDao.getGameInfoNotJoin(param));
        }catch (Exception e) {
            log.error("fail getGameInfoNotJoin with param={}, cause={}", param, Throwables.getStackTraceAsString(e));
            return Response.fail("");
        }
    }


}
