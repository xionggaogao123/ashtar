package com.ashstr.spider.dao;

import com.ashstr.common.base.BaseMyBatisDao;
import com.ashstr.spider.domain.SpiderAdvertiserInfo;
import com.ashstr.spider.domain.TuiaSpiderInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author keven
 * @date 2017-12-15 下午1:49
 * @Description
 */
@Repository
public class SpiderAdvertiserInfoDao extends BaseMyBatisDao<SpiderAdvertiserInfo> {

    public List<TuiaSpiderInfo> getAdvertiserInfo(Map<String, Object> param) {
        return getSqlSession().selectList(sqlId("getAdvertiserInfo"), param);
    }
}
