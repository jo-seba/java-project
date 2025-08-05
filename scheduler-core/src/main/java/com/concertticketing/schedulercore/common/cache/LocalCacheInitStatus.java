package com.concertticketing.schedulercore.common.cache;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Component;

@Component
public class LocalCacheInitStatus {
    private final AtomicBoolean concertLocalCacheReady = new AtomicBoolean(false);

    public boolean isConcertLocalCacheReady() {
        return concertLocalCacheReady.get();
    }

    public void markReady() {
        concertLocalCacheReady.set(true);
    }
}
