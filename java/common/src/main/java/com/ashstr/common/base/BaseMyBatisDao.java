package com.ashstr.common.base;

import com.ashstr.common.domain.Paging;
import com.ashstr.common.domain.PagingCriteria;
import com.ashstr.common.json.JsonMapper;
import com.google.common.collect.Maps;
import org.apache.ibatis.session.SqlSession;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @author keven
 * @date 2018-06-02 下午10:50
 * @Description
 */
public abstract class BaseMyBatisDao<T> {

    @Resource
    protected SqlSession sqlSession;

    /**
     * 添加
     */
    protected static final String INSERT = "insert";

    /**
     * 批量添加
     */
    protected static final String BATCH_INSERT = "batchInsert";

    /**
     * 删除
     */
    protected static final String DELETE = "delete";

    /**
     * 批量删除
     */
    protected static final String BATCH_DELETE = "batchDelete";

    /**
     * 更新
     */
    protected static final String UPDATE = "update";

    /**
     * 单个主键查询对象
     */
    protected static final String SELECT_BY_ID = "selectById";

    /**
     * 主键列表查询对象列表
     */
    protected static final String SELECT_BY_IDS = "selectByIds";

    /**
     * 列表条件查询
     */
    protected static final String SELECT_LIST = "selectList";

    /**
     * 计数
     */
    protected static final String COUNT = "count";

    /**
     * 分页查询
     */
    protected static final String SELECT_PAGING = "selectPaging";


    public final String nameSpace;


    @SuppressWarnings("unchecked")
    public BaseMyBatisDao() {
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            nameSpace = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0]).getSimpleName();
        } else {
            //解决cglib实现aop时转换异常
            nameSpace = ((Class<T>) ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass())
                    .getActualTypeArguments()[0]).getSimpleName();
        }
    }

    /**
     * sql语句的id
     *
     * @param id sql id
     * @return "nameSpace.id"
     */
    protected String sqlId(String id) {
        return nameSpace + "." + id;
    }


    protected SqlSession getSqlSession() {
        return sqlSession;
    }

    /**
     * 添加对象
     *
     * @param t 范型对象
     * @return 增加记录数
     */
    public Integer insert(T t) {
        return sqlSession.insert(sqlId(INSERT), t);
    }

    /**
     * 批量添加对象
     *
     * @param ts 范型对象
     * @return 增加记录数
     */
    public Integer batchInsert(List<T> ts) {
        return sqlSession.insert(sqlId(BATCH_INSERT), ts);
    }

    /**
     * 批量添加对象
     *
     * @param t0 第一个范型对象
     * @param t1 第一个范型对象
     * @param tn 第N个范型对象
     * @return 增加记录数
     */
    @SuppressWarnings("unchecked")
    public Integer batchInsert(T t0, T t1, T... tn) {
        return sqlSession.insert(sqlId(BATCH_INSERT), Arrays.asList(t0, t1, tn));
    }

    /**
     * 删除
     *
     * @param id 主键
     * @return 删除记录数
     */
    public Integer delete(Long id) {
        return sqlSession.delete(sqlId(DELETE), id);
    }

    /**
     * 批量删除
     *
     * @param ids 主键列表
     * @return 删除记录数
     */
    public Integer batchDelete(List<Long> ids) {
        return sqlSession.delete(sqlId(BATCH_DELETE), ids);
    }

    /**
     * 批量删除
     *
     * @param id0 第一个id
     * @param id1 第二个id
     * @param idn 第N个id
     * @return 删除记录数
     */
    public Integer batchDelete(Long id0, Long id1, Long... idn) {
        return sqlSession.delete(sqlId(BATCH_DELETE), Arrays.asList(id0, id1, idn));
    }

    /**
     * 更新对象
     *
     * @param t 范型对象
     * @return 更新记录数
     */
    public Integer update(T t) {
        return sqlSession.update(sqlId(UPDATE), t);
    }


    /**
     * 查询单个对象
     *
     * @param id 主键
     * @return 对象
     */
    public T selectById(Integer id) {
        return selectById(Long.valueOf(id));
    }

    /**
     * 查询单个对象
     *
     * @param id 主键
     * @return 对象
     */
    public T selectById(Long id) {
        return sqlSession.selectOne(sqlId(SELECT_BY_ID), id);
    }

    /**
     * 批量删除
     *
     * @param ids 主键列表
     * @return 删除记录数
     */
    public List<T> selectByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return sqlSession.selectList(sqlId(SELECT_BY_IDS), ids);
    }

    /**
     * 查询对象列表
     *
     * @param id0 第一个id
     * @param id1 第二个id
     * @param idn 第N个id
     * @return 对象列表
     */
    public List<T> selectByIds(Long id0, Long id1, Long... idn) {
        return sqlSession.selectList(sqlId(SELECT_BY_IDS), Arrays.asList(id0, id1, idn));
    }

    /**
     * 查询所有对象列表
     *
     * @return 所有对象列表
     */
    public List<T> selectListAll() {
        return selectList((T) null);
    }

    /**
     * 查询对象列表
     *
     * @param t 范型对象
     * @return 查询到的对象列表
     */
    public List<T> selectList(T t) {
        return sqlSession.selectList(sqlId(SELECT_LIST), t);
    }

    /**
     * 查询对象列表
     *
     * @param criteria Map查询条件
     * @return 查询到的对象列表
     */
    public List<T> selectList(Map<?, ?> criteria) {
        return sqlSession.selectList(sqlId(SELECT_LIST), criteria);
    }

    /**
     * 查询分页对象
     *
     * @param offset 起始偏移
     * @param limit  分页大小
     * @return 查询到的分页对象
     */
    public Paging<T> selectPaging(Integer offset, Integer limit) {
        return selectPaging(offset, limit, new HashMap<>());
    }

    /**
     * 查询分页对象
     *
     * @param offset   起始偏移
     * @param limit    分页大小
     * @param criteria 范型对象, 即查询条件
     * @return 查询到的分页对象
     */
    @SuppressWarnings("unchecked")
    public Paging<T> selectPaging(Integer offset, Integer limit, T criteria) {
        Map<String, Object> params = Maps.newHashMap();
        if (criteria != null) {
            Map<String, Object> objMap = JsonMapper.nonDefaultMapper().getMapper().convertValue(criteria, Map.class);
            params.putAll(objMap);
        }
        // get total count
        Long total = sqlSession.selectOne(sqlId(COUNT), criteria);
        if (total <= 0) {
            return new Paging<T>(0L, Collections.emptyList());
        }
        params.put("offset", offset);
        params.put("limit", limit);
        // get data
        List<T> datas = sqlSession.selectList(sqlId(SELECT_PAGING), params);
        return new Paging<T>(total, datas);
    }

    /**
     * 查询分页对象
     *
     * @param offset   起始偏移
     * @param limit    分页大小
     * @param criteria Map查询条件
     * @return 查询到的分页对象
     */
    public Paging<T> selectPaging(Integer offset, Integer limit, Map<String, Object> criteria) {
        if (criteria == null) {
            criteria = Maps.newHashMap();
        }
        // get total count
        Long total = sqlSession.selectOne(sqlId(COUNT), criteria);
        if (total <= 0) {
            return new Paging<T>(0L, Collections.emptyList());
        }
        criteria.put("offset", offset);
        criteria.put("limit", limit);
        // get data
        List<T> datas = sqlSession.selectList(sqlId(SELECT_PAGING), criteria);
        return new Paging<>(total, datas);
    }

    /**
     * 分页查询，offset， limit都丢在map里面
     *
     * @param criteria 所有查询参数
     * @return 查询到的分页对象
     */
    public Paging<T> selectPaging(PagingCriteria criteria) {
        //如果查询条件为空
        Map<String, Object> param = criteria.toMap();
        // get total count
        Long total = sqlSession.selectOne(sqlId(COUNT), param);
        if (total <= 0) {
            return new Paging<T>(0L, Collections.emptyList());
        }
        // get data
        List<T> datas = sqlSession.selectList(sqlId(SELECT_PAGING), param);
        return new Paging<>(total, datas);
    }

}
