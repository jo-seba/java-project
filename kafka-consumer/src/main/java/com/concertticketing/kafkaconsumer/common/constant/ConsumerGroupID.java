package com.concertticketing.kafkaconsumer.common.constant;

public class ConsumerGroupID {
    public static final String CONCERT_CREATED_DETAIL = "concert-created-detail";
    public static final String ADD_CONCERT_USER_TO_ACTIVE_QUEUE = "add-concert-user-to-active-queue";
    public static final String ADD_CONCERT_USER_TO_EXCLUSIVE_WAITING_QUEUE = "add-concert-user-to-exclusive-waiting-queue";
    public static final String ADD_EXCLUSIVE_CONCERT_TOKEN_TO_HEARTBEAT = "add-exclusive-concert-token-to-heartbeat";
    public static final String CONCERT_DETAIL_VIEW_COUNT = "concert-detail-view-count";

    public static final String CONCERT_SEAT_RESERVATION = "concert-seat-reservation";
    public static final String CONCERT_SEAT_RESERVATION_DLQ_REQUEST = "concert-seat-reservation-dlq-request";
    public static final String CONCERT_REFUND_RETRY = "concert-refund-retry";

    public static final String REMOVE_CONCERT_OLD_TOKENS = "remove-concert-old-tokens";
    public static final String REMOVE_CONCERT_OLD_EXCLUSIVE_TOKENS = "remove-concert-old-exclusive-tokens";
}
