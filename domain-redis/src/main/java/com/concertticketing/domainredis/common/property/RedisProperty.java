package com.concertticketing.domainredis.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@ConfigurationProperties("redis")
public record RedisProperty(
    RedisConnection cache,
    RedisConnection queue
) {
    public record RedisConnection(
        @NotBlank
        String host,
        @Min(1000)
        int port
    ) {
    }
}

