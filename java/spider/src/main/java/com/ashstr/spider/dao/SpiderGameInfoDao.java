package com.ashstr.spider.dao;

import com.ashstr.common.base.BaseMyBatisDao;
import com.ashstr.spider.domain.SpiderGameInfo;
import com.ashstr.spider.domain.TuiaSpiderInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author keven
 * @date 2017-12-15 下午1:51
 * @Description
 */
@Repository
public class SpiderGameInfoDao extends BaseMyBatisDao<SpiderGameInfo> {

    public List<TuiaSpiderInfo> getGameInfoNotJoin(Map<String, Object> param) {
        return sqlSession.selectList(sqlId("getGameInfoNotJoin"), param);
    }

    @Override
    public Integer insert(SpiderGameInfo spiderGameInfo) {
        return sqlSession.insert(sqlId("insert"), spiderGameInfo) ;
    }
}
