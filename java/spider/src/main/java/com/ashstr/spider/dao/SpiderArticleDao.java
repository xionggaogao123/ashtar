package com.ashstr.spider.dao;

import com.ashstr.common.base.BaseMyBatisDao;
import com.ashstr.spider.domain.SpiderArticle;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author keven
 * @date 2017-12-15 上午9:41
 * @Description
 */
@Repository
public class SpiderArticleDao extends BaseMyBatisDao<SpiderArticle> {

    /**
     *  查询id小于lastId内且创建时间大于since的limit个数据, 数据迁移用
     *
     * @param lastId 最大的id
     * @param since 起始更新时间
     * @param limit 数量
     */
    public List<SpiderArticle> listSince(Long lastId, String since, int limit) {
        return getSqlSession().selectList(sqlId("listSince"),
                ImmutableMap.of("lastId", lastId, "limit", limit, "since", since));
    }

    /**
     * 当前最大的id
     *
     * @return 当前最大的id
     */
    public Long maxId() {
        Long id = getSqlSession().selectOne(sqlId("maxId"));
        return MoreObjects.firstNonNull(id, 0L);
    }
}
