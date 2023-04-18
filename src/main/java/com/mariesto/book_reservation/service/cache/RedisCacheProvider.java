package com.mariesto.book_reservation.service.cache;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheProvider implements CacheProvider {
    private final RedisCacheManager redisCacheManager;

    public RedisCacheProvider(RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }

    @Override
    public void put(String cacheName, String key, Object value) {
        final Cache cache = redisCacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }

    @Override
    public Object get(String cacheName, String key) {
        Cache cache = redisCacheManager.getCache(cacheName);
        return cache != null ? cache.get(key) : null;
    }

    @Override
    public void remove(String cacheName, String key) {
        Cache cache = redisCacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }
}
