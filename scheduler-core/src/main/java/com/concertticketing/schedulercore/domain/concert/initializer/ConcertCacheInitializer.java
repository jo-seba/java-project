package com.concertticketing.schedulercore.domain.concert.initializer;

import java.time.LocalDateTime;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.concertticketing.schedulercore.common.cache.LocalCacheInitStatus;
import com.concertticketing.schedulercore.common.cache.LocalCacheManager;
import com.concertticketing.schedulercore.domain.concert.mapper.ConcertMapper;
import com.concertticketing.schedulercore.domain.concert.service.ConcertLocalCacheService;
import com.concertticketing.schedulercore.domain.concert.service.ConcertService;
import com.concertticketing.schedulercore.domain.concert.service.ConcertTicketingConfigService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConcertCacheInitializer {
    private final ConcertMapper concertMapper;

    private final LocalCacheManager localCacheManager;
    private final LocalCacheInitStatus localCacheInitStatus;

    private final ConcertService concertService;

    private final ConcertTicketingConfigService concertTicketingConfigService;

    private final ConcertLocalCacheService concertLocalCacheService;

    @EventListener(ApplicationReadyEvent.class)
    public void initConcertLocalCache() {
        LocalDateTime now = LocalDateTime.now();
        localCacheManager.initLocalCache(
            now,
            23,
            50,
            concertLocalCacheService::putBookableConcerts,
            concertService::findBookableConcerts
        );
        localCacheManager.initLocalCache(
            now,
            23,
            51,
            concertLocalCacheService::putExclusiveConcert,
            date -> concertMapper.toConcertTicketingConfigDto(
                concertTicketingConfigService.findConcertTicketingConfig(date)
                    .orElse(null)
            )
        );
        localCacheInitStatus.markReady();
    }
}
