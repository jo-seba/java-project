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

    public static String concertUserScheduleKey(Long concertId, Long userId, Long scheduleId) {
        return "concert:" + concertId + ":user:" + userId + ":schedule:" + scheduleId;
    }

    public static String concertPaymentEventStateKey(String eventId) {
        return "concert:payment-event:" + eventId + ":state";
    }
}
