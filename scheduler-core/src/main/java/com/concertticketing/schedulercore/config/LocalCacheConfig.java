package com.concertticketing.schedulercore.config;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.concertticketing.schedulercore.common.cache.LocalCacheType;
import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class LocalCacheConfig {
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caches = Arrays.stream(LocalCacheType.values())
            .map(cache -> new CaffeineCache(
                    cache.getCacheName(),
                    Caffeine.newBuilder()
                        .expireAfterWrite(Duration.ofMinutes(cache.getExpiredAfterWrite()))
                        .maximumSize(cache.getMaximumSize())
                        .build()
                )
            ).toList();
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
