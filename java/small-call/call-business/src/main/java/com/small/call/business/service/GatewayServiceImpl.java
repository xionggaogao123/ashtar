package com.small.call.business.service;

import com.ashstr.common.domain.Paging;
import com.small.call.api.criteria.GatewayCriteria;
import com.small.call.api.service.GatewayService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author keven
 * @date 2018-06-10 下午5:36
 * @Description
 */
@Service
public class GatewayServiceImpl implements GatewayService {

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
    public Paging paging(GatewayCriteria criteria) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
