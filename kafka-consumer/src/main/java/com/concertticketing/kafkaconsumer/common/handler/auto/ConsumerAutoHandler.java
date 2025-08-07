package com.concertticketing.kafkaconsumer.common.handler.auto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ConsumerAutoHandler<T> {
    protected void handleEvent(T event) {
        try {
            handler(event);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public abstract void consume(T event);

    public abstract void handler(T event);
}
