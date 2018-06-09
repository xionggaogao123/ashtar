package com.ashstr.spider.service;


import com.ashstr.common.domain.Paging;
import com.ashstr.spider.criteria.SpiderArticleCriteria;
import com.ashstr.spider.dao.SpiderArticleDao;
import com.ashstr.spider.domain.SpiderArticle;
import com.ashstr.spider.enums.SpiderArticleTypeEnums;
import com.ashstr.spider.util.Response;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author keven
 * @date 2017-12-15 上午9:41
 * @Description
 */
@Slf4j
@Service
public class SpiderArticleService {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    private final SpiderArticleDao spiderArticleDao;

    private static final Integer MAX_BATCH = 200;

    @Autowired
    public SpiderArticleService(SpiderArticleDao spiderArticleDao) {
        this.spiderArticleDao = spiderArticleDao;
    }


    public void insertSpiderArticle(SpiderArticle spiderArticle) {
        try {
            spiderArticleDao.insert(spiderArticle);
            Response.ok(spiderArticle.getId());
        } catch (Exception e) {
            /*log.error("fail insert spider article with spiderArticele={}, cause={}",
                    spiderArticle, e.getMessage());*/
            Response.fail("insert.spiderArticle.fail");
        }
    }

    public Response<Paging<SpiderArticle>> paging(SpiderArticleCriteria criteria) {
        try {
            List<Integer> channels = Lists.newArrayList();
            channels.add(SpiderArticleTypeEnums.NEIHAN.getValue());
            channels.add(SpiderArticleTypeEnums.FANJIAN.getValue());
            criteria.setChannels(channels);
            return Response.ok(spiderArticleDao.selectPaging(criteria));
        } catch (Exception e) {
            log.error("fail paging spiderArticle with param={}, cause={}", criteria, Throwables.getStackTraceAsString(e));
            return Response.fail("paging.spiderArticle.fail");
        }
    }

    public List<SpiderArticle> getList(SpiderArticleCriteria articleCriteria) {
        Paging<SpiderArticle> pagingResponse = spiderArticleDao.selectPaging(articleCriteria);
        return pagingResponse.getData();
    }

    public Response<List<SpiderArticle>> listSince() {
        Long maxId = spiderArticleDao.maxId();
        String since = DATE_TIME_FORMAT.print(DateTime.now().withTimeAtStartOfDay().minusDays(1));
        List<SpiderArticle> articles = Lists.newArrayList();
        try {
            while (true) {
                List<SpiderArticle> spiderArticles = spiderArticleDao.listSince(maxId, since, MAX_BATCH);
                if (spiderArticles.isEmpty()) {
                    break;
                }
                articles.addAll(spiderArticles);
                maxId = Iterables.getLast(spiderArticles).getId();
                if (spiderArticles.size() < MAX_BATCH) {
                    break;
                }
            }
            return Response.ok(articles);
        } catch (Exception e) {
            log.error("fail list spiderArticle with lastId={}, since={}, cause={}", maxId, since, Throwables.getStackTraceAsString(e));
            return Response.fail("list.spiderArticle.fail");
        }
    }

}
