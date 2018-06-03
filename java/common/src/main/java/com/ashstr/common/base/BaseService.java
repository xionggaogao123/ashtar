package com.ashstr.common.base;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author keven
 * @date 2018-04-30 下午9:58
 * @Description service 基类
 */
@Slf4j
public abstract class BaseService<T> {

    protected abstract BaseMyBatisDao<T> getEntityDao();

    public T findById(Long id) {
        return getEntityDao().selectById(id);
    }

    public List<T> findByIds(List<Long> ids) {
        return getEntityDao().selectByIds(ids);
    }

    public Long create(T entity) {
        return (long) getEntityDao().insert(entity);
    }

    public boolean update(T entity) {
        return getEntityDao().update(entity) > 0;
    }

    public boolean deleteById(Long id) {
        return getEntityDao().delete(id) > 0;
    }

}
