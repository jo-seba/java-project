package com.concertticketing.kafkaconsumer.common.handler.auto;

public interface ConsumerRetryAutoHandler<T> {
    void consume(T event);

    void handler(T event);

    void handleRetry(T event, Exception e);
}
