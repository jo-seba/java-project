package com.concertticketing.domainredis.domain.concert.repository;

import static com.concertticketing.domainredis.common.constant.CacheRedisKey.*;
import static com.concertticketing.domainredis.common.constant.CacheRedisTTL.*;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.concertticketing.domainredis.common.annotation.CacheRedis;
import com.concertticketing.domainredis.common.annotation.RedisObjectMapper;
import com.concertticketing.domainredis.common.constant.CacheRedisKey;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTicketingCache;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ConcertCacheRepository {
    @CacheRedis
    private final RedisTemplate<String, String> redisTemplate;
    @RedisObjectMapper
    private final ObjectMapper objectMapper;

    public Optional<ConcertTicketingCache> getConcertTicketing(Long concertId) {
        String result = redisTemplate.opsForValue().get(concertTicketingKey(concertId));
        if (result == null) {
            return Optional.empty();
        }

        try {
            return Optional.ofNullable(objectMapper.readValue(result, ConcertTicketingCache.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public void setConcertTicketing(Long concertId, ConcertTicketingCache concertTicketing) {
        try {
            redisTemplate.opsForValue().set(
                concertTicketingKey(concertId),
                objectMapper.writeValueAsString(concertTicketing),
                CONCERT_TICKETING_TTL
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public Optional<ConcertTokenUserCache> getConcertTokenUser(String token) {
        String result = redisTemplate.opsForValue().get(concertTokenUserKey(token));
        if (result == null) {
            return Optional.empty();
        }

        try {
            return Optional.ofNullable(objectMapper.readValue(result, ConcertTokenUserCache.class));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public void setConcertTokenUser(String token, ConcertTokenUserCache concertTokenUser) {
        try {
            redisTemplate.opsForValue().setIfAbsent(
                concertTokenUserKey(token),
                objectMapper.writeValueAsString(concertTokenUser),
                CONCERT_TOKEN_USER_TTL
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public Boolean setConcertTokenUserNX(String token, ConcertTokenUserCache concertTokenUser) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(
                concertTokenUserKey(token),
                objectMapper.writeValueAsString(concertTokenUser),
                CONCERT_TOKEN_USER_TTL
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return Boolean.FALSE;
        }
    }

    public Long unlinkConcertTokenUsers(Set<String> tokens) {
        return redisTemplate.unlink(tokens.stream().map(CacheRedisKey::concertTokenUserKey).toList());
    }

    public Long leftPushConcertOpaqueToken(String token) {
        return redisTemplate.opsForList().leftPush(concertOpaqueTokensKey(), token);
    }

    public Optional<String> leftPopConcertOpaqueToken() {
        return Optional.ofNullable(redisTemplate.opsForList().leftPop(concertOpaqueTokensKey()));
    }

    public Long incrConcertWaitingCount(Long concertId) {
        return redisTemplate.opsForValue().increment(concertWaitingCountKey(concertId));
    }

    public Long getConcertLastWaitingCount(Long concertId) {
        String result = redisTemplate.opsForValue().get(concertWaitingCountLastKey(concertId));
        if (result == null) {
            return 0L;
        }
        return Long.parseLong(result);
    }

    public void setConcertLastWaitingCount(Long concertId, Long count) {
        redisTemplate.opsForValue().set(concertWaitingCountLastKey(concertId), count.toString());
    }
}