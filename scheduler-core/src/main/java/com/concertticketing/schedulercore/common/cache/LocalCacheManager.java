package com.concertticketing.schedulercore.common.cache;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocalCacheManager {
    private final CacheManager cacheManager;

    public <T> void put(LocalCacheType localCacheType, String key, T value) {
        if (value != null) {
            getCache(localCacheType.getCacheName())
                .put(key, value);
        }
    }

    public <T> Optional<T> get(LocalCacheType localCacheType, String key, Class<T> clazz) {
        return Optional.ofNullable(cacheManager.getCache(localCacheType.getCacheName()))
            .map(cache -> cache.get(key))
            .map(Cache.ValueWrapper::get) // 이 부분까지 map으로 변경
            .map(clazz::cast);
    }

    public <T> List<T> get(LocalCacheType localCacheType, String key) {
        Cache cache = cacheManager
            .getCache(localCacheType.getCacheName());
        if (cache == null) {
            return Collections.emptyList();
        }
        Cache.ValueWrapper valueWrapper = cache.get(key);
        if (valueWrapper == null || valueWrapper.get() == null) {
            return Collections.emptyList();
        }
        return (List<T>)valueWrapper.get();
    }

    /**
     * 앱 시작 시 로컬 캐시 초기화하는 메서드
     * 기준 날짜(now)를 기반으로 값 업데이트 후, threshold 값과 비교해 다음 날까지 업데이트
     * @param now: 기준 날짜
     * @param thresholdHour: scheduler가 원래 실행되어야 하는 시간(23:50:00 cron -> 23)
     * @param thresholdMinute: scheduler가 원래 실행되어야 하는 분(23:50:00 cron -> 50)
     * @param cacheWriter: cache 쓰는 메서드
     * @param finder: 디비 조회하는 메서드
     */
    public <T> void initLocalCache(
        LocalDateTime now,
        int thresholdHour,
        int thresholdMinute,
        BiConsumer<LocalDate, T> cacheWriter,
        Function<LocalDate, T> finder
    ) {
        LocalDate targetDate = now.toLocalDate();
        cacheWriter.accept(targetDate, finder.apply(targetDate));

        if (isAfterThreshold(now, thresholdHour, thresholdMinute)) {
            LocalDate nextDate = targetDate.plusDays(1);
            cacheWriter.accept(nextDate, finder.apply(nextDate));
        }
    }

    private boolean isAfterThreshold(LocalDateTime now, int thresholdHour, int thresholdMinute) {
        return now.getHour() > thresholdHour
            || (now.getHour() == thresholdHour && now.getMinute() >= thresholdMinute);
    }

    private Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            throw new IllegalArgumentException("Cache " + cacheName + " not found");
        }
        return cache;
    }
}
