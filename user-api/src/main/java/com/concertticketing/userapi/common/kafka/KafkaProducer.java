package com.concertticketing.userapi.common.kafka;

import java.util.concurrent.CompletableFuture;

import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.concertticketing.userapi.common.annotation.DefaultKafkaTemplate;
import com.concertticketing.userapi.common.annotation.LowLatencyKafkaTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {
    @DefaultKafkaTemplate
    private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;
    @LowLatencyKafkaTemplate
    private final KafkaTemplate<String, SpecificRecord> lowLatencyKafkaTemplate;

    public void send(String topic, SpecificRecord record) {
        CompletableFuture<SendResult<String, SpecificRecord>> result = kafkaTemplate.send(topic, record);
        result.whenComplete((sendResult, ex) -> {
            if (ex != null) {
                // Handle the exception
                log.error("Failed to send message: " + ex.getMessage());
            }
        });
    }

    public void sendLowLatency(String topic, SpecificRecord record) {
        CompletableFuture<SendResult<String, SpecificRecord>> result = lowLatencyKafkaTemplate.send(topic, record);
        result.whenComplete((sendResult, ex) -> {
            if (ex != null) {
                // Handle the exception
                log.error("Failed to send low latency message: " + ex.getMessage());
            }
        });
    }
}
