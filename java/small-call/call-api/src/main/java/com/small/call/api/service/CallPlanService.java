package com.small.call.api.service;

import com.ashstr.common.domain.Paging;

/**
 * @author keven
 * @date 2018-06-10 下午5:25
 * @Description
 */
public interface CallPlanService<CallPlan> extends BaseService{

    Paging<CallPlan> paging();
}
