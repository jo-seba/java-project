package com.concertticketing.kafkaconsumer.common.handler.auto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ConsumerDLQAutoHandler<T> extends ConsumerAutoHandler<T> {
    @Override
    protected void handleEvent(T event) {
        try {
            handler(event);
        } catch (Exception e) {
            log.error("Error handling event: {}", e.getMessage());
            handleDLQ(event, e);
        }
    }

    public abstract void handleDLQ(T event, Exception e);
}
