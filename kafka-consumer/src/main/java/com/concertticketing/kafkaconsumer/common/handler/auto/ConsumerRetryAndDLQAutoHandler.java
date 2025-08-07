package com.concertticketing.kafkaconsumer.common.handler.auto;

public interface ConsumerRetryAndDLQAutoHandler<T> {
    void consume(T event);

    void handler(T event);

    void handleRetry(T event, Exception e);

    void handleDLQ(T event, Exception e);
}
