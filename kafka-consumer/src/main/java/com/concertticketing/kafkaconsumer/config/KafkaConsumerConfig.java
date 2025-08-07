package com.concertticketing.kafkaconsumer.config;

import static com.concertticketing.kafkaconsumer.common.constant.ConsumerFactoryName.*;

import java.util.Map;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    private final KafkaProperties kafkaProperties;

    @Bean(name = SINGLE_AUTO_ACK_CONSUMER_FACTORY)
    public ConcurrentKafkaListenerContainerFactory<String, SpecificRecord> singleAutoAckConsumerFactory() {
        return defaultKafkaListenerContainerFactory(
            true,
            ContainerProperties.AckMode.BATCH,
            false
        );
    }

    private ConcurrentKafkaListenerContainerFactory<String, SpecificRecord> defaultKafkaListenerContainerFactory(
        boolean autoCommitEnabled,
        ContainerProperties.AckMode ackMode,
        boolean isBatchListener
    ) {
        ConcurrentKafkaListenerContainerFactory<String, SpecificRecord> factory = new ConcurrentKafkaListenerContainerFactory<>();

        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommitEnabled);

        factory.setConsumerFactory(
            new DefaultKafkaConsumerFactory<>(props)
        );
        factory.getContainerProperties().setAckMode(ackMode);
        factory.setBatchListener(isBatchListener);

        return factory;
    }
}
