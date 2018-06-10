package com.small.call.business.service;

import com.ashstr.common.domain.Paging;
import com.small.call.api.criteria.TopicCriteria;
import com.small.call.api.domain.Topic;
import com.small.call.api.service.TopicService;
import com.small.call.business.dao.TopicDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author keven
 * @date 2018-06-10 下午5:37
 * @Description
 */
@Service
public class TopicSerivceImpl implements TopicService {

    @Resource
    private TopicDao topicDao;

    @Override
    public Long create(Object object) {
        return (long)topicDao.insert((Topic) object);
    }

    @Override
    public Topic findById(Long id) {
        return topicDao.selectById(id);
    }

    @Override
    public List<Topic> findByIds(List ids) {
        return topicDao.selectByIds(ids);
    }

    @Override
    public boolean update(Object o) {
        return topicDao.update((Topic)o) > 0;
    }

    @Override
    public Paging paging(TopicCriteria criteria) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return topicDao.delete(id) > 0;
    }
}
