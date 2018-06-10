package com.small.call.api.service;

import com.ashstr.common.domain.Paging;
import com.small.call.api.criteria.CustomerCriteria;


/**
 * @author keven
 * @date 2018-06-10 下午5:23
 * @Description
 */
public interface CustomerService<Customer> extends BaseService{

    Paging<Customer> paging(CustomerCriteria criteria);

}
