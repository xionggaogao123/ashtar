package com.small.call.api.service;

import com.ashstr.common.domain.Paging;
import com.small.call.api.criteria.UserCriteria;
import com.small.call.api.domain.User;


/**
 * @author keven
 * @date 2018-06-10 下午5:21
 * @Description
 */
public interface UserService<User> extends BaseService {

    //Long create(User user);

    Paging<User> paging(UserCriteria criteria);

}
