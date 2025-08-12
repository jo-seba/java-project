package com.concertticketing.domainredis.domain.concert.domain;

import com.concertticketing.domainredis.common.constant.ConcertSeatReservationStatus;

public record ConcertSeatReservationCache(
    String eventId,
    Long concertId,
    Long userId,
    String billingKey,
    Long scheduleId,
    Long areaId,
    int seatRow,
    int seatColumn,
    ConcertSeatReservationStatus status
) {

    public boolean canReservationEventStart() {
        return status == ConcertSeatReservationStatus.ONGOING;
    }

    public boolean isWaiting() {
        return status == ConcertSeatReservationStatus.WAITING;
    }
}
