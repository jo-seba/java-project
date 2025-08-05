package com.concertticketing.userapi.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.concertticketing.commonavro.UserJoinedExclusiveQueueEvent;
import com.concertticketing.commonavro.UserJoinedWaitingQueueEvent;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    private final KafkaProperties kafkaProperties;

    @Bean
    public KafkaTemplate<String, UserJoinedWaitingQueueEvent> userJoinedWaitingKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
    }

    @Bean
    public KafkaTemplate<String, UserJoinedExclusiveQueueEvent> userJoinedExclusiveKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
    }

    private Map<String, Object> producerConfigs() {
        return kafkaProperties.buildProducerProperties();
    }
}
