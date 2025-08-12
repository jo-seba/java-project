package com.concertticketing.domainredis.common.constant;

public final class CacheRedisKey {
    private CacheRedisKey() {
    }

    public static String concertTicketingKey(Long concertId) {
        return "concert:" + concertId + ":ticketing";
    }

    public static String concertTokenUserKey(String token) {
        return "concert:token-user:" + token;
    }

    public static String concertOpaqueTokensKey() {
        return "concert:opaque-tokens";
    }

    public static String concertWaitingCountKey(Long concertId) {
        return "concert:" + concertId + ":waiting-count";
    }

    public static String concertWaitingCountLastKey(Long concertId) {
        return "concert:" + concertId + ":waiting-count:last";
    }

    public static String concertListSortKey(String sort) {
        return "concert:list:sort:" + sort;
    }
}
