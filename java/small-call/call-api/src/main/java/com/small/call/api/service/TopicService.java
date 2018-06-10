package com.small.call.api.service;

import com.ashstr.common.domain.Paging;
import com.small.call.api.criteria.TopicCriteria;


/**
 * @author keven
 * @date 2018-06-10 下午5:21
 * @Description
 */
public interface TopicService<Topic> extends BaseService {


    Paging<Topic> paging(TopicCriteria criteria);


}
