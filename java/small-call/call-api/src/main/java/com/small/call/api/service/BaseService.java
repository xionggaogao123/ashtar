package com.small.call.api.service;


import java.util.List;

/**
 * @author keven
 * @date 2018-06-10 下午5:55
 * @Description
 */
public interface BaseService<T> {

    Long create(T t);

    T findById(Long id);

    List<T> findByIds(List<Long> ids);

    boolean update(T t);

    boolean delete(Long id);
}
