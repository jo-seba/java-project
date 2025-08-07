package com.concertticketing.kafkaconsumer.common.handler.manual;

import org.springframework.kafka.support.Acknowledgment;

public abstract class ConsumerDLQManualHandler<T> extends ConsumerManualHandler<T> {
    @Override
    protected void handleEvent(T event, Acknowledgment ack) {
        try {
            handler(event, ack);
        } catch (Exception e) {
            handleDLQ(event, ack, e);
        } finally {
            ack.acknowledge();
        }
    }

    public abstract void handleDLQ(T event, Acknowledgment ack, Exception e);
}
