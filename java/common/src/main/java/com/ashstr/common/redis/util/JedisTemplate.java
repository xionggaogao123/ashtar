package com.ashstr.common.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

/**
 * @author keven
 * @date 2018-06-02 下午11:18
 * @Description
 */
public class JedisTemplate {

    private static Logger logger = LoggerFactory.getLogger(JedisTemplate.class);

    private Pool<Jedis> jedisPool;

    public JedisTemplate(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }

    public <T> T execute(JedisTemplate.JedisAction<T> jedisAction) throws JedisException {
        return (T) this.execute((JedisAction)jedisAction, 0);
    }

    public <T> T execute(JedisTemplate.JedisAction<T> jedisAction, int dbIndex) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;

        Object obj;
        try {
            jedis = this.jedisPool.getResource();
            jedis.select(dbIndex);
            obj = jedisAction.action(jedis);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            broken = true;
            throw e;
        } finally {
            this.closeResource(jedis, broken);
        }

        return (T) obj;
    }

    public void execute(JedisTemplate.JedisActionNoResult jedisAction) throws JedisException {
        this.execute(jedisAction, 0);
    }

    public void execute(JedisTemplate.JedisActionNoResult jedisAction, int dbIndex) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;

        try {
            jedis = this.jedisPool.getResource();
            jedis.select(dbIndex);
            jedisAction.action(jedis);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            broken = true;
            throw e;
        } finally {
            this.closeResource(jedis, broken);
        }

    }

    protected void closeResource(Jedis jedis, boolean connectionBroken) {
        if(jedis != null) {
            if(connectionBroken) {
                this.jedisPool.returnBrokenResource(jedis);
            } else {
                this.jedisPool.returnResource(jedis);
            }
        }

    }

    public Pool<Jedis> getJedisPool() {
        return this.jedisPool;
    }

    public interface JedisActionNoResult {
        void action(Jedis jedis);
    }

    public interface JedisAction<T> {
        T action(Jedis jedis);
    }

}
