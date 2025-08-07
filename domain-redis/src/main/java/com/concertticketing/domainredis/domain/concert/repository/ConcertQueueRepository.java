package com.concertticketing.domainredis.domain.concert.repository;

import static com.concertticketing.domainredis.common.constant.QueueRedisKey.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import com.concertticketing.domainredis.common.annotation.QueueRedisClient;
import com.concertticketing.domainredis.common.annotation.QueueRedisTemplate;
import com.concertticketing.domainredis.common.redis.RedisClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ConcertQueueRepository {
    @QueueRedisClient
    private final RedisClient redisClient;

    @QueueRedisTemplate
    private final RedisTemplate<String, String> redisTemplate;

    private final RedisScript<List> retainFirstPopRestScript;

    public boolean addConcertActiveTokenNX(String token, long createdAt) {
        return redisClient.zAddIfAbsent(
            concertActiveTokensKey(),
            token,
            createdAt
        );
    }

    /**
     * @param maxScore: where 0 < score < maxScore
     */
    public Set<String> getConcertActiveTokens(long maxScore) {
        return redisClient.zRangeByScore(
            concertActiveTokensKey(),
            0,
            maxScore
        );
    }

    public Long removeConcertActiveTokens(Object[] tokens) {
        return redisClient.zRemoveBulk(
            concertActiveTokensKey(),
            tokens
        );
    }

    public List<String> retainFirstPopRestConcertUserToken(Long concertId, Long userId) {
        List<?> results = redisTemplate.execute(
            retainFirstPopRestScript,
            List.of(concertUserTokensKey(concertId, userId))
        );
        return results.stream()
            .filter(Objects::nonNull)
            .map(Object::toString)
            .collect(Collectors.toList());
    }

    public Long leftPushConcertUserToken(Long concertId, Long userId, String token) {
        return redisClient.leftPush(
            concertUserTokensKey(concertId, userId),
            token
        );
    }

    public List<String> getConcertWaitingTokens(Long concertId, long minScore, long maxScore, long limit) {
        return redisClient.zRangeByScore(
                concertWaitingTokensKey(concertId),
                minScore,
                maxScore,
                0,
                limit
            ).stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public boolean addConcertWaitingTokenNX(Long concertId, String token, long createdAt) {
        return redisClient.zAddIfAbsent(
            concertWaitingTokensKey(concertId),
            token,
            createdAt
        );
    }

    public Long removeConcertWaitingTokens(Long concertId, Object[] tokens) {
        return redisClient.zRemoveBulk(
            concertWaitingTokensKey(concertId),
            tokens
        );
    }

    /**
     * @param maxScore: where 0 < score < maxScore
     */
    public Set<String> getConcertActiveTokens(Long concetId, long maxScore) {
        return redisClient.zRangeByScore(
            concertActiveTokensKey(concetId),
            0,
            maxScore
        );
    }

    public Long getConcertActiveTokenCount(Long concertId) {
        return redisClient.zCard(concertActiveTokensKey(concertId));
    }

    public Long addConcertActiveTokens(Long concertId, long timestamp, List<String> tokens) {
        Double score = (double)timestamp;
        return redisClient.zAddBulk(
            concertActiveTokensKey(concertId),
            tokens.stream()
                .map(token -> new DefaultTypedTuple<>(token, score))
                .collect(Collectors.toSet())
        );
    }

    public Long removeConcertActiveTokens(Long concertId, Object[] tokens) {
        return redisClient.zRemoveBulk(
            concertActiveTokensKey(concertId),
            tokens
        );
    }

    /**
     * @param maxScore: where 0 < score < maxScore
     */
    public Set<String> getConcertWaitingTokenHeartbeats(Long concertId, long maxScore) {
        return redisClient.zRangeByScore(
            concertWaitingTokenHeartbeatsKey(concertId),
            0,
            maxScore
        );
    }

    public void addConcertWaitingTokenHeartbeat(Long concertId, String token, long timestamp) {
        redisClient.zAdd(
            concertWaitingTokenHeartbeatsKey(concertId),
            token,
            timestamp
        );
    }

    public Long removeConcertWaitingTokenHeartbeats(Long concertId, Object[] tokens) {
        return redisClient.zRemoveBulk(
            concertWaitingTokenHeartbeatsKey(concertId),
            tokens
        );
    }
}
