package com.concertticketing.userapi.common.kafka;

import java.util.concurrent.CompletableFuture;

import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;

    public void send(String topic, SpecificRecord record) {
        CompletableFuture<SendResult<String, SpecificRecord>> result = kafkaTemplate.send(topic, record);
        result.whenComplete((sendResult, ex) -> {
            if (ex != null) {
                // Handle the exception
                log.error("Failed to send message: " + ex.getMessage());
            }
        });
    }
}
