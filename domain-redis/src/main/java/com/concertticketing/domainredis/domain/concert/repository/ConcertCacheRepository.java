package com.concertticketing.domainredis.domain.concert.repository;

import static com.concertticketing.domainredis.common.constant.CacheRedisKey.*;
import static com.concertticketing.domainredis.common.constant.CacheRedisTTL.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.concertticketing.domainredis.common.annotation.CacheRedisClient;
import com.concertticketing.domainredis.common.constant.ConcertPaymentEventStatus;
import com.concertticketing.domainredis.common.redis.RedisClient;
import com.concertticketing.domainredis.domain.concert.domain.ConcertListCache;
import com.concertticketing.domainredis.domain.concert.domain.ConcertSeatReservationCache;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTicketingCache;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ConcertCacheRepository {
    @CacheRedisClient
    private final RedisClient redisClient;

    public Optional<ConcertTicketingCache> getConcertTicketing(Long concertId) {
        return redisClient.get(
            concertTicketingKey(concertId),
            ConcertTicketingCache.class
        );
    }

    public void setConcertTicketing(Long concertId, ConcertTicketingCache concertTicketing) {
        redisClient.set(
            concertTicketingKey(concertId),
            concertTicketing,
            CONCERT_TICKETING_TTL
        );
    }

    public Optional<ConcertTokenUserCache> getConcertTokenUser(String token) {
        return redisClient.get(
            concertTokenUserKey(token),
            ConcertTokenUserCache.class
        );
    }

    public void setConcertTokenUser(String token, ConcertTokenUserCache concertTokenUser) {
        redisClient.set(
            concertTokenUserKey(token),
            concertTokenUser,
            CONCERT_TOKEN_USER_TTL
        );
    }

    public boolean setConcertTokenUserNX(String token, ConcertTokenUserCache concertTokenUser) {
        return redisClient.setIfNotExist(
            concertTokenUserKey(token),
            concertTokenUser,
            CONCERT_TOKEN_USER_TTL
        );
    }

    public Long unlinkConcertTokenUsers(Set<String> tokens) {
        return redisClient.unlinkBulk(tokens);
    }

    public Long leftPushConcertOpaqueToken(String token) {
        return redisClient.leftPush(
            concertOpaqueTokensKey(),
            token
        );
    }

    public Optional<String> leftPopConcertOpaqueToken() {
        return redisClient.leftPop(concertOpaqueTokensKey());
    }

    public Long incrConcertWaitingCount(Long concertId) {
        return redisClient.incr(concertWaitingCountKey(concertId));
    }

    public Long getConcertLastWaitingCount(Long concertId) {
        return redisClient.get(
            concertWaitingCountLastKey(concertId)
        ).map(Long::parseLong).orElse(0L);
    }

    public void setConcertLastWaitingCount(Long concertId, Long count) {
        redisClient.set(
            concertWaitingCountLastKey(concertId),
            count.toString()
        );
    }

    public void setConcerts(String sort, List<ConcertListCache> concerts) {
        redisClient.set(concertListSortKey(sort), concerts);
    }

    public List<ConcertListCache> getConcerts(String sort) {
        return redisClient.getList(concertListSortKey(sort), ConcertListCache.class);
    }

    public void setConcertSeatReservation(
        Long concertId,
        Long userId,
        Long scheduleId,
        ConcertSeatReservationCache value
    ) {
        redisClient.set(
            concertUserScheduleKey(concertId, userId, scheduleId),
            value,
            CONCERT_TOKEN_USER_TTL
        );
    }

    public Optional<ConcertSeatReservationCache> getConcertSeatReservation(
        Long concertId,
        Long userId,
        Long scheduleId
    ) {
        return redisClient.get(
            concertUserScheduleKey(concertId, userId, scheduleId),
            ConcertSeatReservationCache.class
        );
    }

    public boolean setConcertPaymentEventStatus(String eventId, ConcertPaymentEventStatus status) {
        return redisClient.setIfNotExist(
            concertPaymentEventStateKey(eventId),
            status,
            CONCERT_PAYMENT_EVENT_STATE_TTL
        );
    }
}