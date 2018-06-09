package com.ashstr.spider.dao;

import com.ashstr.common.base.BaseMyBatisDao;
import com.ashstr.spider.domain.SpiderImage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author keven
 * @date 2017-12-15 下午2:23
 * @Description
 */
@Repository
public class SpiderImageDao extends BaseMyBatisDao<SpiderImage> {

    public void fetchBatch(Map<String, Object> param) {
        getSqlSession().insert(sqlId("fetchBatch"), param);
    }

    public List<String> getInTimeSlot(Map<String, Object> param) {
        return getSqlSession().selectList(sqlId("getInTimeSlot"), param);
    }
}
