package com.concertticketing.domainredis.domain.concert.domain;

import java.time.Instant;

public record ConcertTicketingCache(
    long bookingStartedAt,
    long bookingEndedAt,
    Integer capacity
) {
    public boolean isBookingActive() {
        long now = Instant.now().getEpochSecond();
        return bookingStartedAt <= now && now < bookingEndedAt;
    }

    public boolean isQueueExclusive() {
        return capacity != null;
    }
}
