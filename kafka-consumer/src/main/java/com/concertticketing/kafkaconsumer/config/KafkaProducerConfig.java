package com.concertticketing.kafkaconsumer.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.concertticketing.commonavro.ExclusiveUserTokenExpiredEvent;
import com.concertticketing.commonavro.UserTokenExpiredEvent;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {
    private final KafkaProperties kafkaProperties;

    @Bean
    public KafkaTemplate<String, UserTokenExpiredEvent> userTokenExpiredKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
    }

    @Bean
    public KafkaTemplate<String, ExclusiveUserTokenExpiredEvent> exclusiveUserTokenExpiredKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
    }

    private Map<String, Object> producerConfigs() {
        return kafkaProperties.buildProducerProperties();
    }
}
