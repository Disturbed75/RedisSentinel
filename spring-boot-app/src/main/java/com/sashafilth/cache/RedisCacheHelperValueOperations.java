package com.sashafilth.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheHelperValueOperations implements CacheHelper  {

    private static final String SEPARATOR_CACHE = ":";

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisCacheHelperValueOperations(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void put(String cacheName, Object key, Object value, long ttl) {
        redisTemplate.opsForValue().set(constructKey(cacheName, key), value, ttl, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String cacheName, Object key) {
        return redisTemplate.opsForValue().get(constructKey(cacheName, key));
    }

    @Override
    public Map<String, Object> getWithExpDetails(String cacheName, Object key) {
        Map<String, Object> res = new HashMap<>();
        String validKey = constructKey(cacheName, key);
        Long exp = redisTemplate.getExpire(validKey, TimeUnit.SECONDS);
        Object body = redisTemplate.opsForValue().get(validKey);
        res.put("exp", exp);
        res.put("body", body);
        return res;
    }

    @Override
    public void remove(String cacheName, Object key) {
//        redisTemplate.exp
        redisTemplate.opsForValue().getOperations().delete(constructKey(cacheName, key));
    }

    private String constructKey(String cacheName, Object key) {
        return cacheName + SEPARATOR_CACHE + key;
    }
}
