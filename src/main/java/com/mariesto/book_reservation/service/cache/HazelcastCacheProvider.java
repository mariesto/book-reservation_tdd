package com.mariesto.book_reservation.service.cache;

import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import com.hazelcast.spring.cache.HazelcastCacheManager;

@Component
public class HazelcastCacheProvider implements CacheProvider {
    private final HazelcastCacheManager hazelcastCacheManager;

    public HazelcastCacheProvider(HazelcastCacheManager hazelcastCacheManager) {
        this.hazelcastCacheManager = hazelcastCacheManager;
    }

    @Override
    public void put(String cacheName, String key, Object value) {
        Cache cache = hazelcastCacheManager.getCache(cacheName);
        cache.put(key, value);
    }

    @Override
    public Object get(String cacheName, String key) {
        Cache cache = hazelcastCacheManager.getCache(cacheName);
        return cache.get(key);
    }

    @Override
    public void remove(String cacheName, String key) {
        Cache cache = hazelcastCacheManager.getCache(cacheName);
        cache.evict(key);
    }
}
