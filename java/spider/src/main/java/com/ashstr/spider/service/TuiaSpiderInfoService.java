package com.ashstr.spider.service;

import com.ashstr.spider.dao.TuiaSpiderInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author keven
 * @date 2017-12-15 下午2:03
 * @Description
 */
@Slf4j
@Service
public class TuiaSpiderInfoService {

    private final TuiaSpiderInfoDao tuiaSpiderInfoDao;

    @Autowired
    public TuiaSpiderInfoService(TuiaSpiderInfoDao tuiaSpiderInfoDao) {
        this.tuiaSpiderInfoDao = tuiaSpiderInfoDao;
    }


}
