package com.ashstr.spider.listener;

import com.ashstr.spider.domain.SpiderArticle;
import com.ashstr.spider.event.InsertArticleEvent;
import com.ashstr.spider.service.SpiderArticleService;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author keven
 * @date 2017-12-15 下午5:03
 * @Description
 */
@Slf4j
@Service
public class InsertArticleListener {

    private final EventBus eventBus;

    private final SpiderArticleService spiderArticleService;

    @Autowired
    public InsertArticleListener(SpiderArticleService spiderArticleService, EventBus eventBus) {
        this.spiderArticleService = spiderArticleService;
        this.eventBus = eventBus;
    }

    @PostConstruct
    public void register() {
        eventBus.register(this);
    }

    @Subscribe
    public void saveSpiderArticleData(InsertArticleEvent articleEvent) {
        List<SpiderArticle> articles = articleEvent.getSpiderArticles();
        for (SpiderArticle article : articles) {
            try {
                spiderArticleService.insertSpiderArticle(article);
            } catch (Exception e) {
                //日志太多了，会把堆栈干掉
                //log.error("fail insert article with article={}, cause={}", article, e.getMessage());
            }
        }
    }
}
