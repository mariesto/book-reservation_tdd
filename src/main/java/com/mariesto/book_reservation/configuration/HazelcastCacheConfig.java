package com.mariesto.book_reservation.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;

@Configuration
@EnableCaching
public class HazelcastCacheConfig {
    @Bean
    HazelcastInstance hazelcastInstance() {
        return Hazelcast.newHazelcastInstance();
    }

    @Bean (name = "hazelcastCacheManager")
    public HazelcastCacheManager hazelcastCacheManager() {
        return new HazelcastCacheManager(hazelcastInstance());
    }
}
