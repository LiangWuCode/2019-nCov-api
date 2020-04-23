package com.wuliang.ncov.core.redis;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 重写，实现接口并且继承抽象类
 */
@Repository("redisDao")
public class RedisDaoImpl extends AbstractBaseRedisDao<Object, Object> implements RedisDao {

    @Override
    public boolean existsKey(final Object key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public Set<Object> keys(final Object pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public boolean delete(final Object key) {
        return redisTemplate.delete(key);
    }

    @Override
    public int count(final Object key) {
        return redisTemplate.keys(key).size();
    }

    @Override
    public long deletePattern(final Object pattern) {
        Set<Object> keys = redisTemplate.keys(pattern);
        if ((keys != null ? keys.size() : 0) > 0) {
            return redisTemplate.delete(keys);
        } else {
            return 0;
        }
    }

    @Override
    public void delete(final String[] keys) {
        for (String key : keys) {
            delete(key);
        }
    }

    @Override
    public long delete(final Set<Object> keys) {
        return redisTemplate.delete(keys);
    }

    @Override
    public boolean vSet(final Object key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean vSet(final Object key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean vSetUpdate(final Object key, Long expireTime) {
        boolean result = false;
        try {
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Object vGet(final Object key) {
        Object result = null;
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    @Override
    public void hmSet(Object key, Object hashKey, Object value) {
        HashOperations<Object, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    @Override
    public void hmSetAll(Object key, Map<Object, Object> map) {
        HashOperations<Object, Object, Object> hash = redisTemplate.opsForHash();
        hash.putAll(key, map);
    }

    @Override
    public Map<Object, Object> hmGet(Object key) {
        HashOperations<Object, Object, Object> hash = redisTemplate.opsForHash();
        return hash.entries(key);
    }

    @Override
    public Object hmGet(Object key, Object hashKey) {
        HashOperations<Object, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    @Override
    public Object hmDel(Object key, Object hashKey) {
        HashOperations<Object, Object, Object> hash = redisTemplate.opsForHash();
        return hash.delete(key, hashKey);
    }

    @Override
    public long lSize(Object k) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        return list.size(k);
    }

    @Override
    public Object lRange(Object k) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        return list.range(k, 0, list.size(k));
    }

    @Override
    public List<?> lRange(Object k, long start, long end) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        return list.range(k, start, end);
    }

    @Override
    public Object lindexFirst(Object k) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        return list.index(k, 0);
    }

    @Override
    public Object lindex(Object k, long index) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        return list.index(k, index);
    }

    @Override
    public void lLeftPush(Object k, Object v) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        list.leftPush(k, v);
    }

    @Override
    public void lLeftPush(Object k, Object v, boolean bool) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        if (bool) {
            list.remove(k, list.size(k), v);
        }
        list.leftPush(k, v);
    }

    @Override
    public void lLeftPushAll(Object k, List<Object> lst) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        list.leftPushAll(k, lst);
    }

    @Override
    public void lRightPush(Object k, Object v, boolean bool) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        if (bool) {
            list.remove(k, list.size(k), v);
        }
        list.rightPush(k, v);
    }

    @Override
    public void lRightPush(Object k, Object v) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    @Override
    public void lRightPushAll(Object k, List<Object> lst) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        list.rightPushAll(k, lst);
    }



    @Override
    public Object lLeftPop(Object k) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        return list.leftPop(k);
    }

    @Override
    public Object lRightPop(Object k) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        return list.rightPop(k);
    }

    @Override
    public long lRemove(Object k, long count) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        return list.remove(k, 0, null);
    }

    @Override
    public long lRemove(Object k, long count, Object v) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        return list.remove(k, count, v);
    }

    @Override
    public long lRemove(Object k, Object v) {
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        return list.remove(k, list.size(k), v);
    }

    @Override
    public void sAdd(Object key, Object value) {
        SetOperations<Object, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    @Override
    public Set<Object> sMembers(Object key) {
        SetOperations<Object, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    @Override
    public void zAdd(Object key, Object value, double scoure) {
        ZSetOperations<Object, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    @Override
    public Set<Object> rangeByScore(Object key, double scoure, double scoure1) {
        ZSetOperations<Object, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    @Override
    public void hmSetIncrement(Object key, Object hashKey, Long value) {
        HashOperations<Object, Object, Object> hash = redisTemplate.opsForHash();
        hash.increment(key, hashKey, value);
    }}
