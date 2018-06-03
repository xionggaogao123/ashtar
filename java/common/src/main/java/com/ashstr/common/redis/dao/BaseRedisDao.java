package com.ashstr.common.redis.dao;

import com.ashstr.common.redis.util.JedisTemplate;
import com.ashstr.common.redis.util.KeyUtils;
import com.ashstr.common.redis.util.StringHashMapper;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author keven
 * @date 2018-06-02 下午11:05
 * @Description
 */
public abstract class BaseRedisDao<T> {


    public final StringHashMapper<T> stringHashMapper;
    protected final JedisTemplate template;
    protected final Class<T> entityClass;

    public BaseRedisDao(JedisTemplate template) {
        this.template = template;
        this.entityClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.stringHashMapper = new StringHashMapper(this.entityClass);
    }

    public List<T> findByIds(Iterable<String> ids) {
        return this.findByKeys(ids, id -> KeyUtils.entityId(BaseRedisDao.this.entityClass, id));
    }


    public List<T> findByKeys(final Iterable<String> keys, final Function<String, String> keyGen) {
        if (Iterables.isEmpty(keys)) {
            return Collections.emptyList();
        } else {
            List<Response<Map<String, String>>> result = this.template.execute(jedis -> {
                List<Response<Map<String, String>>> result1 = Lists.newArrayListWithCapacity(Iterables.size(keys));
                Pipeline p = jedis.pipelined();

                for (String key : keys) {
                    result1.add(p.hgetAll(keyGen.apply(key)));
                }

                p.sync();
                return result1;
            });
            List<T> entities = Lists.newArrayListWithCapacity(result.size());

            for (Object aResult : result) {
                Response<Map<String, String>> mapResponse = (Response) aResult;
                entities.add((T) this.stringHashMapper.fromHash((Map) mapResponse.get()));
            }

            return entities;
        }
    }

    protected T findByKey(final Long id) {
        Map<String, String> hash = this.template.execute(jedis -> {
            return jedis.hgetAll(KeyUtils.entityId(BaseRedisDao.this.entityClass, id));
        });
        return this.stringHashMapper.fromHash(hash);
    }

    protected T findByKey(final String key) {
        Map<String, String> hash = this.template.execute(jedis -> {
            return jedis.hgetAll(KeyUtils.entityId(BaseRedisDao.this.entityClass, key));
        });
        return this.stringHashMapper.fromHash(hash);
    }

    public Long newId() {
        return this.template.execute(jedis -> {
            return jedis.incr(KeyUtils.entityCount(BaseRedisDao.this.entityClass));
        });
    }
}
