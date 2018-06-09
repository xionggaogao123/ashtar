package com.ashstr.spider.service;


import com.ashstr.spider.dao.SpiderImageDao;
import com.ashstr.spider.domain.SpiderImage;
import com.ashstr.spider.util.Response;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author keven
 * @date 2017-12-15 下午2:25
 * @Description
 */
@Slf4j
@Service
public class SpiderImageService {

    private final SpiderImageDao spiderImageDao;

    @Autowired
    public SpiderImageService(SpiderImageDao spiderImageDao) {
        this.spiderImageDao = spiderImageDao;
    }

    public Response<List<String>> getInTimeSlot(Map<String, Object> param) {
        try {
           return Response.ok(spiderImageDao.getInTimeSlot(param));
        } catch (Exception e) {
            log.error("fail getInTimeSlot with param={}, cause={}", param, Throwables.getStackTraceAsString(e));
            return Response.fail("list.image.fail");
        }
    }

    public void fetchBatch(Map<String, Object> param) {
        try {
            spiderImageDao.fetchBatch(param);
        } catch (Exception e) {
            log.error("fail fetchBatch imageUrl with param={}, cause={}", param, Throwables.getStackTraceAsString(e));
            Response.fail("fetchBatch.imageUrl.fail");
        }
    }

    public void batchInsertSpiderImage(List<SpiderImage> spiderImages) {
        try {
            spiderImageDao.batchInsert(spiderImages);
        } catch (Exception e) {
            log.error("batch insert spiderImage fail cause={} ", Throwables.getStackTraceAsString(e));
            Response.fail("insert.spiderImage.fail");
        }
    }

}
