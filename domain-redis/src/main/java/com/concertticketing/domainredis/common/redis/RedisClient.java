package com.concertticketing.domainredis.common.redis;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.CollectionUtils;

import com.concertticketing.commonerror.exception.common.CommonInternalServerException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RedisClient {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    // String operations
    public Optional<String> get(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    public <T> Optional<T> get(String key, Class<T> clazz) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key))
            .flatMap(value -> {
                try {
                    return Optional.ofNullable(objectMapper.readValue(value, clazz));
                } catch (Exception e) {
                    throw new CommonInternalServerException(e);
                }
            });
    }

    public <T> void set(String key, T value) {
        setValue(key, value, null);
    }

    public <T> void set(String key, T value, Duration ttl) {
        setValue(key, value, ttl);
    }

    public <T> boolean setIfNotExist(String key, T value) {
        return setValueIfNotExist(key, value, null);
    }

    public <T> boolean setIfNotExist(String key, T value, Duration ttl) {
        return setValueIfNotExist(key, value, ttl);
    }

    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    // list operations
    public Optional<String> leftPop(String key) {
        return Optional.ofNullable(
            redisTemplate.opsForList()
                .leftPop(key)
        );
    }

    public Long leftPush(String key, String value) {
        if (value == null) {
            throw new CommonInternalServerException(
                String.format("RedisClient.leftPush\nkey: %s / invalid value: null", key)
            );
        }
        return redisTemplate.opsForList()
            .leftPush(key, value);
    }

    // sorted set operations
    public Set<String> zRangeByScore(String key, double min, double max) {
        return Optional.ofNullable(redisTemplate.opsForZSet().rangeByScore(key, min, max))
            .orElseGet(Collections::emptySet);
    }

    public Set<String> zRangeByScore(String key, double min, double max, long offset, long limit) {
        return Optional.ofNullable(redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, limit))
            .orElseGet(Collections::emptySet);
    }

    public Long zCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    public void zAdd(String key, String value, double score) {
        if (value == null) {
            throw new CommonInternalServerException(
                String.format("RedisClient.zAdd\nkey: %s / invalid value: null", key)
            );
        }

        redisTemplate.opsForZSet().add(key, value, score);
    }

    public boolean zAddIfAbsent(String key, String value, double score) {
        if (value == null) {
            throw new CommonInternalServerException(
                String.format("RedisClient.zAddIfAbsent\nkey: %s / invalid value: null", key)
            );
        }

        Boolean result = redisTemplate.opsForZSet().addIfAbsent(key, value, score);
        return Boolean.TRUE.equals(result);
    }

    public Long zAddBulk(String key, Set<ZSetOperations.TypedTuple<String>> tuples) {
        if (CollectionUtils.isEmpty(tuples)) {
            throw new CommonInternalServerException(
                String.format("RedisClient.zAddBulk\nkey: %s / invalid value: null", key)
            );
        }
        return redisTemplate.opsForZSet().add(key, tuples);
    }

    public Long zRemoveBulk(String key, Object[] tokens) {
        if (tokens == null || tokens.length == 0) {
            return 0L;
        }
        return redisTemplate.opsForZSet().remove(key, tokens);
    }

    // common operations
    public Long unlinkBulk(Collection<String> tokens) {
        if (CollectionUtils.isEmpty(tokens)) {
            return 0L;
        }
        return redisTemplate.unlink(tokens);
    }

    private <T> void setValue(String key, T value, Duration ttl) {
        if (value == null) {
            throw new CommonInternalServerException(
                String.format("RedisClient.setValue\nkey: %s / invalid value: null", key)
            );
        }

        try {
            String data = isString(value) ? (String)value : objectMapper.writeValueAsString(value);
            if (ttl != null) {
                redisTemplate.opsForValue().set(
                    key,
                    data,
                    ttl
                );
            } else {
                redisTemplate.opsForValue().set(
                    key,
                    data
                );
            }
        } catch (Exception e) {
            throw new CommonInternalServerException(e);
        }
    }

    private <T> boolean setValueIfNotExist(String key, T value, Duration ttl) {
        if (value == null) {
            throw new CommonInternalServerException(
                String.format("RedisClient.setValueIfNotExist\nkey: %s / invalid value: null", key)
            );
        }

        try {
            String data = isString(value) ? (String)value : objectMapper.writeValueAsString(value);
            Boolean result;
            if (ttl != null) {
                result = redisTemplate.opsForValue().setIfAbsent(
                    key,
                    data,
                    ttl
                );
            } else {
                result = redisTemplate.opsForValue().setIfAbsent(
                    key,
                    data
                );
            }
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            throw new CommonInternalServerException(e);
        }
    }

    private boolean isString(Object value) {
        return value instanceof String;
    }
}
