package com.concertticketing.userapi.apis.concerts.usecase;

import static com.concertticketing.commonutils.TimeUtils.*;

import java.util.stream.Stream;

import com.concertticketing.commonavro.UserJoinedExclusiveQueueEvent;
import com.concertticketing.commonavro.UserJoinedWaitingQueueEvent;
import com.concertticketing.commonerror.exception.common.CommonBadRequestException;
import com.concertticketing.commonerror.exception.common.CommonConflictException;
import com.concertticketing.commonutils.TokenUtils;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTicketingCache;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.concertticketing.userapi.apis.concerts.dto.ConcertTicketingEntryDto.ConcertTicketingEntryRes;
import com.concertticketing.userapi.apis.concerts.dto.ConcertTicketingStatusDto.ConcertTicketingStatusRes;
import com.concertticketing.userapi.apis.concerts.mapper.ConcertMapper;
import com.concertticketing.userapi.apis.concerts.service.ConcertCacheCreateService;
import com.concertticketing.userapi.apis.concerts.service.ConcertCacheDeleteService;
import com.concertticketing.userapi.apis.concerts.service.ConcertCacheSearchService;
import com.concertticketing.userapi.apis.concerts.service.ConcertCacheUpdateService;
import com.concertticketing.userapi.apis.concerts.service.ConcertSearchService;
import com.concertticketing.userapi.common.annotation.UseCase;
import com.concertticketing.userapi.common.kafka.KafkaProducer;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ConcertTicketingUseCase {
    private final KafkaProducer kafkaProducer;

    private final ConcertMapper concertMapper;
    private final ConcertSearchService concertSearchService;

    private final ConcertCacheCreateService concertCacheCreateService;
    private final ConcertCacheSearchService concertCacheSearchService;
    private final ConcertCacheUpdateService concertCacheUpdateService;
    private final ConcertCacheDeleteService concertCacheDeleteService;

    private final int MAX_TOKEN_GENERATION_RETRIES = 5;

    public ConcertTicketingEntryRes attemptTicketingEntry(Long userId, Long concertId) {
        ConcertTicketingCache concertTicketingCache = concertCacheSearchService
            .getConcertTicketing(concertId)
            .orElseGet(() -> {
                ConcertTicketingCache newCachedConcert = concertMapper.toConcertTicketingCache(
                    concertSearchService.findWithTicketingQueueConfig(concertId)
                );
                concertCacheCreateService.setConcertTicketing(concertId, newCachedConcert);
                return newCachedConcert;
            });

        if (!concertTicketingCache.isBookingActive()) {
            throw new CommonBadRequestException();
        }

        ConcertTokenUserCache concertTokenUserCache = ConcertTokenUserCache.of(
            userId,
            concertId,
            concertTicketingCache.isQueueExclusive()
                ? concertCacheUpdateService.incrConcertWaitingCount(concertId)
                : null
        );

        String token = Stream.generate(() ->
                concertCacheDeleteService.popConcertOpaqueToken()
                    .orElseGet(TokenUtils::opaqueTokenGenerate)
            )
            .limit(MAX_TOKEN_GENERATION_RETRIES)
            .filter(t -> concertCacheCreateService.setConcertTokenUserNX(
                t,
                concertTokenUserCache
            ))
            .findFirst()
            .orElseThrow(CommonConflictException::new);

        long createdAt = epochSeconds();
        if (concertTicketingCache.isQueueExclusive()) {
            kafkaProducer.sendConcertUserJoinedExclusiveQueue(
                new UserJoinedExclusiveQueueEvent(
                    userId,
                    concertId,
                    token,
                    createdAt
                )
            );
        } else {
            kafkaProducer.sendConcertUserJoinedWaitingQueue(
                new UserJoinedWaitingQueueEvent(
                    userId,
                    concertId,
                    token,
                    createdAt
                )
            );
        }

        return new ConcertTicketingEntryRes(
            token,
            concertTicketingCache.isQueueExclusive()
        );
    }

    public ConcertTicketingStatusRes getTicketingStatus(ConcertTokenUserCache concertTokenUserCache) {
        Long lastWaitingCount = concertCacheSearchService.getConcertLastWaitingCount(concertTokenUserCache.concertId());
        Long remainingWaitingCount = concertTokenUserCache.lastWaitingCount() - lastWaitingCount;
        return new ConcertTicketingStatusRes(
            concertTokenUserCache.status(),
            remainingWaitingCount > 0
                ? remainingWaitingCount
                : 12L
        );
    }
}
