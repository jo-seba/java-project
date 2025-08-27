package com.concertticketing.schedulercore.common.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LocalCacheType {
    BOOKABLE_CONCERTS("bookableConcerts", 24 * 60 + 12, 1000),
    EXCLUSIVE_CONCERT("exclusiveConcert", 24 * 60 + 11, 1000);

    private final String cacheName;
    /**
     * minutes
     */
    private final int expiredAfterWrite;
    private final int maximumSize;
}
