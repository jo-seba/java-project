package com.concertticketing.kafkaconsumer.common.handler.manual;

import org.springframework.kafka.support.Acknowledgment;

public interface ConsumerRetryManualHandler<T> {
    void consume(T event, Acknowledgment ack);

    void handler(T event, Acknowledgment ack);

    void handleRetry(T event, Acknowledgment ack, Exception e);
}
