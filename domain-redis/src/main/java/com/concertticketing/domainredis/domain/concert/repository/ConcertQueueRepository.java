package com.concertticketing.domainredis.domain.concert.repository;

import static com.concertticketing.domainredis.common.constant.QueueRedisKey.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.concertticketing.domainredis.common.annotation.QueueRedis;
import com.concertticketing.domainredis.common.annotation.RedisObjectMapper;
import com.concertticketing.domainredis.common.annotation.RetainFirstPopRestScript;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ConcertQueueRepository {
    @QueueRedis
    private final RedisTemplate<String, String> redisTemplate;

    @RetainFirstPopRestScript
    private final RedisScript<List> retainFirstPopRestScript;

    @RedisObjectMapper
    private final ObjectMapper objectMapper;

    public Boolean addConcertActiveTokenNX(String token, long createdAt) {
        return redisTemplate.opsForZSet().addIfAbsent(
            concertActiveTokensKey(),
            token,
            createdAt
        );
    }

    /**
     * @param maxScore: where 0 < score < maxScore
     */
    public Set<String> getConcertActiveTokens(long maxScore) {
        return redisTemplate.opsForZSet().rangeByScore(
            concertActiveTokensKey(),
            0,
            maxScore
        );
    }

    public Long removeConcertActiveTokens(Object[] tokens) {
        return redisTemplate.opsForZSet().remove(concertActiveTokensKey(), tokens);
    }

    public List<String> retainFirstPopRestConcertUserToken(Long concertId, Long userId) {
        List<?> results = redisTemplate.execute(
            retainFirstPopRestScript,
            List.of(concertUserTokensKey(concertId, userId))
        );
        if (results == null) {
            return Collections.emptyList();
        }
        return results.stream()
            .filter(Objects::nonNull)
            .map(Object::toString)
            .collect(Collectors.toList());
    }

    public Long leftPushConcertUserToken(Long concertId, Long userId, String token) {
        return redisTemplate.opsForList().leftPush(
            concertUserTokensKey(concertId, userId),
            token
        );
    }

    public List<String> getConcertWaitingTokens(Long concertId, long minScore, long maxScore, long limit) {
        Set<String> activeTokens = redisTemplate.opsForZSet().rangeByScore(
            concertWaitingTokensKey(concertId),
            minScore,
            maxScore,
            0,
            limit
        );
        if (CollectionUtils.isEmpty(activeTokens)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(activeTokens);
    }

    public Boolean addConcertWaitingTokenNX(Long concertId, String token, long createdAt) {
        return redisTemplate.opsForZSet().addIfAbsent(
            concertWaitingTokensKey(concertId),
            token,
            createdAt
        );
    }

    public Long removeConcertWaitingTokens(Long concertId, Object[] tokens) {
        return redisTemplate.opsForZSet().remove(concertWaitingTokensKey(concertId), tokens);
    }

    /**
     * @param maxScore: where 0 < score < maxScore
     */
    public Set<String> getConcertActiveTokens(Long concetId, long maxScore) {
        return redisTemplate.opsForZSet().rangeByScore(
            concertActiveTokensKey(concetId),
            0,
            maxScore
        );
    }

    public Long getConcertActiveTokenCount(Long concertId) {
        return redisTemplate.opsForZSet().zCard(concertActiveTokensKey(concertId));
    }

    public Long addConcertActiveTokens(Long concertId, long timestamp, List<String> tokens) {
        Double score = Double.valueOf(timestamp);
        return redisTemplate.opsForZSet().add(
            concertActiveTokensKey(concertId),
            tokens.stream()
                .map(token -> new DefaultTypedTuple<>(token, score))
                .collect(Collectors.toSet())
        );
    }

    public Long removeConcertActiveTokens(Long concertId, Object[] tokens) {
        return redisTemplate.opsForZSet().remove(concertActiveTokensKey(concertId), tokens);
    }

    /**
     * @param maxScore: where 0 < score < maxScore
     */
    public Set<String> getConcertWaitingTokenHeartbeats(Long concertId, long maxScore) {
        return redisTemplate.opsForZSet().rangeByScore(
            concertWaitingTokenHeartbeatsKey(concertId),
            0,
            maxScore
        );
    }

    public void addConcertWaitingTokenHeartbeat(Long concertId, String token, long timestamp) {
        redisTemplate.opsForZSet().add(concertWaitingTokenHeartbeatsKey(concertId), token, timestamp);
    }

    public Long removeConcertWaitingTokenHeartbeats(Long concertId, Object[] tokens) {
        return redisTemplate.opsForZSet().remove(concertWaitingTokenHeartbeatsKey(concertId), tokens);
    }
}
