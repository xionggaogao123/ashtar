package com.small.call.business.service;

import com.ashstr.common.domain.Paging;
import com.small.call.api.criteria.DetailTopicCriteria;
import com.small.call.api.domain.DetailTopic;
import com.small.call.api.service.DetailTopicService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author keven
 * @date 2018-06-10 下午5:36
 * @Description
 */
@Service
public class DetailTopicServiceImpl implements DetailTopicService {

    @Override
    public Long create(Object o) {
        return null;
    }

    @Override
    public Object findById(Long id) {
        return null;
    }

    @Override
    public List findByIds(List ids) {
        return null;
    }

    @Override
    public boolean update(Object o) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Paging<DetailTopic> paging(DetailTopicCriteria criteria) {
        return null;
    }
}
