package com.ashstr.spider.service;


import com.ashstr.spider.dao.SpiderUserInfoDao;
import com.ashstr.spider.domain.SpiderUserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author keven
 * @date 2018-06-08 下午4:25
 * @Description
 */
@Service
public class SpiderUserInfoService {

    @Resource
    private SpiderUserInfoDao spiderUserInfoDao;

    public Integer createUserInfo(SpiderUserInfo userInfo) {
        return spiderUserInfoDao.insert(userInfo);
    }


    public List<SpiderUserInfo> listAll() {
        return spiderUserInfoDao.selectListAll();
    }

}
