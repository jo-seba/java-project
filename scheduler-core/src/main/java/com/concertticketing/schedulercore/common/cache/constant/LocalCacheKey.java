package com.concertticketing.schedulercore.common.cache.constant;

import java.time.LocalDate;

public final class LocalCacheKey {
    private LocalCacheKey() {
    }

    public static String bookableConcertsKey(LocalDate date) {
        return date.toString() + ":" + "bookableConcerts";
    }

    public static String exclusiveConcertKey(LocalDate date) {
        return date.toString() + ":" + "exclusiveConcerts";
    }
}
