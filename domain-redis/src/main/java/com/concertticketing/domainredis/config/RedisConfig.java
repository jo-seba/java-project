package com.concertticketing.domainredis.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.concertticketing.domainredis.common.annotation.CacheRedisClient;
import com.concertticketing.domainredis.common.annotation.CacheRedisConnectionFactory;
import com.concertticketing.domainredis.common.annotation.CacheRedisTemplate;
import com.concertticketing.domainredis.common.annotation.QueueRedisClient;
import com.concertticketing.domainredis.common.annotation.QueueRedisConnectionFactory;
import com.concertticketing.domainredis.common.annotation.QueueRedisTemplate;
import com.concertticketing.domainredis.common.annotation.RedisObjectMapper;
import com.concertticketing.domainredis.common.property.RedisProperty;
import com.concertticketing.domainredis.common.redis.RedisClient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RedisConfig {
    @Bean
    @Primary
    @CacheRedisConnectionFactory
    public RedisConnectionFactory cacheLettuceConnectionFactory(RedisProperty redisProperty) {
        return new LettuceConnectionFactory(
            new RedisStandaloneConfiguration(
                redisProperty.cache().host(),
                redisProperty.cache().port()
            )
        );
    }

    @Bean
    @QueueRedisConnectionFactory
    public RedisConnectionFactory queueLettuceConnectionFactory(RedisProperty redisProperty) {
        return new LettuceConnectionFactory(
            new RedisStandaloneConfiguration(
                redisProperty.queue().host(),
                redisProperty.queue().port()
            )
        );
    }

    @Bean
    @Primary
    @CacheRedisTemplate
    public RedisTemplate<String, String> cacheRedisTemplate(
        @CacheRedisConnectionFactory
        RedisConnectionFactory connectionFactory
    ) {
        return createStringRedisTemplate(connectionFactory);
    }

    @Bean
    @QueueRedisTemplate
    public RedisTemplate<String, String> queueRedisTemplate(
        @QueueRedisConnectionFactory
        RedisConnectionFactory connectionFactory
    ) {
        return createStringRedisTemplate(connectionFactory);
    }

    @Bean
    @RedisObjectMapper
    public ObjectMapper redisObjectMapper() {
        return new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Bean
    @CacheRedisClient
    public RedisClient cacheRedisClient(
        @CacheRedisTemplate
        RedisTemplate<String, String> redisTemplate,
        @RedisObjectMapper
        ObjectMapper objectMapper
    ) {
        return new RedisClient(
            redisTemplate,
            objectMapper
        );
    }

    @Bean
    @QueueRedisClient
    public RedisClient queueRedisClient(
        @QueueRedisTemplate
        RedisTemplate<String, String> redisTemplate,
        @RedisObjectMapper
        ObjectMapper objectMapper
    ) {
        return new RedisClient(
            redisTemplate,
            objectMapper
        );
    }

    @Bean
    public RedisScript<List> retainFirstPopRestScript() {
        Resource scriptSource = new ClassPathResource("scripts/retain_first_pop_rest.lua");
        return RedisScript.of(scriptSource, List.class);
    }

    private RedisTemplate<String, String> createStringRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());

        return template;
    }
}
