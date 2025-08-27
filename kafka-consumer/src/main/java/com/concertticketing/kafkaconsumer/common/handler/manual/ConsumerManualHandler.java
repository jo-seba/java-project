package com.concertticketing.kafkaconsumer.common.handler.manual;

import org.springframework.kafka.support.Acknowledgment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ConsumerManualHandler<T> {
    protected void handleEvent(T event, Acknowledgment ack) {
        try {
            handler(event, ack);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            ack.acknowledge();
        }
    }

    public abstract void consume(T event, Acknowledgment ack);

    public abstract void handler(T event, Acknowledgment ack);
}
