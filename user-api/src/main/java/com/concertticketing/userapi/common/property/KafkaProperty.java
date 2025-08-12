package com.concertticketing.userapi.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kafka.producer")
public record KafkaProperty(
    KafkaProducerOption defaultOption,
    KafkaProducerOption lowLatency
) {
    public record KafkaProducerOption(
        int lingerMs,
        int batchSize
    ) {
    }
}
