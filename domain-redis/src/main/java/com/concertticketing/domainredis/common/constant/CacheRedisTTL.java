package com.concertticketing.domainredis.common.constant;

import java.time.Duration;

public final class CacheRedisTTL {
    private CacheRedisTTL() {
    }

    public static final Duration CONCERT_TICKETING_TTL = Duration.ofMinutes(10);
    public static final Duration CONCERT_TOKEN_USER_TTL = Duration.ofMinutes(30);
    public static final Duration CONCERT_PAYMENT_EVENT_STATE_TTL = Duration.ofMinutes(5);
}
