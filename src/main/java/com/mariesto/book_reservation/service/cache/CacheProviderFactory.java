package com.mariesto.book_reservation.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CacheProviderFactory {

    @Autowired
    @Qualifier("redisCacheProvider")
    private RedisCacheProvider redisCacheProvider;

    @Autowired
    @Qualifier("hazelcastCacheProvider")
    private HazelcastCacheProvider hazelcastCacheProvider;

    @Value ("${cache-type}")
    private String cacheType;

    @Bean
    public CacheProvider getCacheProvider() {
        switch (cacheType) {
            case "redis":
                return redisCacheProvider;
            case "hazelcast":
                return hazelcastCacheProvider;
            default:
                throw new IllegalArgumentException("Invalid cache provider type.");
        }
    }
}
