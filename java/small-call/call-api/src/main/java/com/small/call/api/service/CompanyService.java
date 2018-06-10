package com.small.call.api.service;

import com.ashstr.common.domain.Paging;

/**
 * @author keven
 * @date 2018-06-10 下午5:24
 * @Description
 */
public interface CompanyService<Company> extends BaseService {

    Paging<Company> paging();

}
