package com.small.call.api.service;

import com.ashstr.common.domain.Paging;
import com.small.call.api.criteria.SipCriteria;


/**
 * @author keven
 * @date 2018-06-10 下午5:21
 * @Description
 */
public interface SipService<Sip> extends BaseService {

    Paging<Sip> paging(SipCriteria criteria);

}
