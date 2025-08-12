package com.concertticketing.commonkafka;

public final class KafkaTopic {
    private KafkaTopic() {
    }

    public static final String CONCERT_CREATED = "concert-created";
    public static final String CONCERT_USER_JOINED_WAITING_QUEUE = "concert-user-joined-waiting-queue";
    public static final String CONCERT_USER_JOINED_EXCLUSIVE_QUEUE = "concert-user-joined-exclusive-queue";
    public static final String CONCERT_USER_TOKEN_EXPIRED = "concert-user-token-expired";
    public static final String CONCERT_EXCLUSIVE_USER_TOKEN_EXPIRED = "concert-exclusive-user-token-expired";
    public static final String CONCERT_DETAIL_REQUESTED_EVENT = "concert-detail-requested-event";

    public static final String CONCERT_SEAT_RESERVATION_REQUESTED = "concert-seat-reservation-requested";
    public static final String CONCERT_SEAT_RESERVATION_DLQ = "concert-seat-reservation-dlq";
}
