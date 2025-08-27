package com.concertticketing.userapi.config;

import java.util.Map;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.concertticketing.userapi.common.annotation.DefaultKafkaTemplate;
import com.concertticketing.userapi.common.annotation.LowLatencyKafkaTemplate;
import com.concertticketing.userapi.common.property.KafkaProperty;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {
    private final KafkaProperties kafkaProperties;

    @Bean
    @DefaultKafkaTemplate
    public KafkaTemplate<String, SpecificRecord> kafkaTemplate(
        KafkaProperty kafkaProperty
    ) {
        Map<String, Object> config = kafkaProperties.buildProducerProperties();
        config.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProperty.defaultOption().lingerMs());
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProperty.defaultOption().batchSize());
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(config));
    }

    @Bean
    @LowLatencyKafkaTemplate
    public KafkaTemplate<String, SpecificRecord> lowLatencyKafkaTemplate(
        KafkaProperty kafkaProperty
    ) {
        Map<String, Object> config = kafkaProperties.buildProducerProperties();
        config.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProperty.lowLatency().lingerMs());
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProperty.lowLatency().batchSize());
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(config));
    }
}
