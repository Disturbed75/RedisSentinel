package com.sashafilth.cache;

public interface CacheHelper {

    void put(String cacheName, Object key, Object value, long ttl);

    Object get(String cacheName, Object key);

    void remove(String cacheName, Object key);
}
