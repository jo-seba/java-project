package com.concertticketing.userapi.apis.concerts.facade;

import static com.concertticketing.commonkafka.KafkaTopic.*;
import static com.concertticketing.commonutils.TimeUtils.*;

import java.util.stream.Stream;

import com.concertticketing.commonavro.UserJoinedExclusiveQueueEvent;
import com.concertticketing.commonavro.UserJoinedWaitingQueueEvent;
import com.concertticketing.commonerror.exception.common.CommonBadRequestException;
import com.concertticketing.commonerror.exception.common.CommonConflictException;
import com.concertticketing.commonutils.TokenUtils;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTicketingCache;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.concertticketing.userapi.apis.concerts.dto.ConcertTicketingEntryDto;
import com.concertticketing.userapi.apis.concerts.dto.ConcertTicketingStatusDto;
import com.concertticketing.userapi.apis.concerts.mapper.ConcertMapper;
import com.concertticketing.userapi.apis.concerts.service.ConcertCacheService;
import com.concertticketing.userapi.apis.concerts.service.ConcertService;
import com.concertticketing.userapi.common.annotation.Facade;
import com.concertticketing.userapi.common.kafka.KafkaProducer;

import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class ConcertTicketingFacade {
    private final KafkaProducer kafkaProducer;

    private final ConcertMapper concertMapper;

    private final ConcertService concertService;

    private final ConcertCacheService concertCacheService;

    private final int MAX_TOKEN_GENERATION_RETRIES = 5;

    public ConcertTicketingEntryDto.ConcertTicketingEntryRes attemptTicketingEntry(Long userId, Long concertId) {
        ConcertTicketingCache concertTicketingCache = concertCacheService
            .getConcertTicketing(concertId)
            .orElseGet(() -> {
                ConcertTicketingCache newCachedConcert = concertMapper.toConcertTicketingCache(
                    concertService.findConcertTicketingInfo(concertId)
                );
                concertCacheService.setConcertTicketing(concertId, newCachedConcert);
                return newCachedConcert;
            });

        if (!concertTicketingCache.isBookingActive()) {
            throw new CommonBadRequestException();
        }

        ConcertTokenUserCache concertTokenUserCache = ConcertTokenUserCache.of(
            userId,
            concertId,
            concertTicketingCache.isQueueExclusive()
                ? concertCacheService.incrConcertWaitingCount(concertId)
                : null
        );

        String token = Stream.generate(() ->
                concertCacheService.popConcertOpaqueToken()
                    .orElseGet(TokenUtils::opaqueTokenGenerate)
            )
            .limit(MAX_TOKEN_GENERATION_RETRIES)
            .filter(t -> concertCacheService.setConcertTokenUserNX(
                t,
                concertTokenUserCache
            ))
            .findFirst()
            .orElseThrow(CommonConflictException::new);

        long createdAt = epochSeconds();
        if (concertTicketingCache.isQueueExclusive()) {
            kafkaProducer.sendLowLatency(
                CONCERT_USER_JOINED_EXCLUSIVE_QUEUE,
                new UserJoinedExclusiveQueueEvent(
                    userId,
                    concertId,
                    token,
                    createdAt
                )
            );
        } else {
            kafkaProducer.sendLowLatency(
                CONCERT_USER_JOINED_WAITING_QUEUE,
                new UserJoinedWaitingQueueEvent(
                    userId,
                    concertId,
                    token,
                    createdAt
                )
            );
        }

        return new ConcertTicketingEntryDto.ConcertTicketingEntryRes(
            token,
            concertTicketingCache.isQueueExclusive()
        );
    }

    public ConcertTicketingStatusDto.ConcertTicketingStatusRes getTicketingStatus(
        ConcertTokenUserCache concertTokenUserCache) {
        Long lastWaitingCount = concertCacheService.getConcertLastWaitingCount(concertTokenUserCache.concertId());
        Long remainingWaitingCount = concertTokenUserCache.lastWaitingCount() - lastWaitingCount;
        return new ConcertTicketingStatusDto.ConcertTicketingStatusRes(
            concertTokenUserCache.status(),
            remainingWaitingCount > 0
                ? remainingWaitingCount
                : 12L
        );
    }
}
