package com.ashstr.spider.service;

import com.ashstr.spider.dao.TuiaAppDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author keven
 * @date 2017-12-15 下午2:02
 * @Description
 */
@Slf4j
@Service
public class TuiaAppService {

    private final TuiaAppDao tuiaAppDao;

    @Autowired
    public TuiaAppService (TuiaAppDao tuiaAppDao) {
        this.tuiaAppDao = tuiaAppDao;
    }


}
