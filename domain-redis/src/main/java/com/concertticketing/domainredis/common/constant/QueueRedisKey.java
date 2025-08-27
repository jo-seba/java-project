package com.concertticketing.domainredis.common.constant;

public final class QueueRedisKey {
    private QueueRedisKey() {
    }

    public static String concertActiveTokensKey() {
        return "concert:active-tokens";
    }

    public static String concertActiveTokensKey(Long concertId) {
        return "concert:" + concertId + ":active-tokens";
    }

    public static String concertUserTokensKey(Long concertId, Long userId) {
        return "concert:" + concertId + ":user:" + userId + ":tokens";
    }

    public static String concertWaitingTokensKey(Long concertId) {
        return "concert:" + concertId + ":waiting-tokens";
    }

    public static String concertWaitingTokenHeartbeatsKey(Long concertId) {
        return "concert:" + concertId + ":waiting-token-heartbeats";
    }
}
