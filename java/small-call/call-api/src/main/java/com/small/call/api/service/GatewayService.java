package com.small.call.api.service;


import com.ashstr.common.domain.Paging;
import com.small.call.api.criteria.GatewayCriteria;


/**
 * @author keven
 * @date 2018-06-10 下午5:21
 * @Description
 */
public interface GatewayService<Gateway> extends BaseService{

    Paging<Gateway> paging(GatewayCriteria criteria);

}
