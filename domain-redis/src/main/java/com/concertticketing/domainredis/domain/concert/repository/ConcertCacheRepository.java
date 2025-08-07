package com.concertticketing.domainredis.domain.concert.repository;

import static com.concertticketing.domainredis.common.constant.CacheRedisKey.*;
import static com.concertticketing.domainredis.common.constant.CacheRedisTTL.*;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.concertticketing.domainredis.common.annotation.CacheRedisClient;
import com.concertticketing.domainredis.common.redis.RedisClient;
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
}