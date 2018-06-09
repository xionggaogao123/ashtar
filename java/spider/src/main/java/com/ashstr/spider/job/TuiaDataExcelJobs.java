package com.ashstr.spider.job;

import com.ashstr.spider.logic.TuiaDataExcelLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author keven
 * @date 2017-12-17 下午5:32
 * @Description
 */
@Slf4j
@RestController
public class TuiaDataExcelJobs {

    private TuiaDataExcelLogic tuiaDataExcelLogic;

    @Scheduled(cron = "0 0 10 * * ?")
    public void sendExcelToOP() {
        tuiaDataExcelLogic.sendExcelToOP();
    }
}
