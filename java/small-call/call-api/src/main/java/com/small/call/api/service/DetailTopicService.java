package com.small.call.api.service;

import com.ashstr.common.domain.Paging;
import com.small.call.api.criteria.DetailTopicCriteria;
import com.small.call.api.domain.DetailTopic;


/**
 * @author keven
 * @date 2018-06-10 下午5:22
 * @Description
 */
public interface DetailTopicService extends BaseService{

    Paging<DetailTopic> paging(DetailTopicCriteria criteria);

}
