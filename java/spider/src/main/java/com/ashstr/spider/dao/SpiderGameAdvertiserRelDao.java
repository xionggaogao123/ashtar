package com.ashstr.spider.dao;

import com.ashstr.common.base.BaseMyBatisDao;
import com.ashstr.spider.domain.SpiderGameAdvertiserRel;
import com.ashstr.spider.domain.TuiaSpiderInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author keven
 * @date 2017-12-15 下午1:50
 * @Description
 */
@Repository
public class SpiderGameAdvertiserRelDao extends BaseMyBatisDao<SpiderGameAdvertiserRel> {

    public List<TuiaSpiderInfo> getRelNotJoin(Map<String, Object> param) {
        return getSqlSession().selectList(sqlId("getRelNotJoin"),param);
    }
}
