package com.concertticketing.domainredis.common.constant;

import java.time.Duration;

public final class QueueRedisTTL {
    private QueueRedisTTL() {
    }

    public static final Duration CONCERT_WAITING_TOKENS_TTL = Duration.ofSeconds(30);
    public static final Duration CONCERT_ACTIVE_TOKENS_TTL = Duration.ofMinutes(10);
}
