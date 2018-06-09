package com.ashstr.spider.job;

import com.ashstr.spider.logic.SpiderArticleLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author keven
 * @date 2017-12-17 下午1:43
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("/spider/article")
public class SpiderArticleJobs {

    @Autowired
    private SpiderArticleLogic spiderArticleLogic;

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 凌晨1点 执行爬 数据操作
     */
    @RequestMapping(value = "spider", produces = MediaType.APPLICATION_JSON_VALUE)
    @Scheduled(cron = "0 0 1 * * ?")
    public void spiderArticleDate() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    spiderArticleLogic.spiderNeiHan(new Date());
                    spiderArticleLogic.spiderBuDeJie();
                    spiderArticleLogic.spiderFanJian();
                    spiderArticleLogic.spiderPengFu();
                } catch (Exception e) {
                    log.error("fail spider article cause={}", e.getMessage());
                }
            }
        });
    }


}
