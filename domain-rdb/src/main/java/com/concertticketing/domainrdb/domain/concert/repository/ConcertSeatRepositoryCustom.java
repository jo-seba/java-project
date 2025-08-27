package com.concertticketing.domainrdb.domain.concert.repository;

import java.time.LocalDateTime;

public interface ConcertSeatRepositoryCustom {
    boolean reserveConcertSeat(
        Long concertId,
        Long userId,
        Long scheduleId,
        Long areaId,
        int seatRow,
        int seatColumn,
        LocalDateTime holdExpiredAt
    );

    boolean ongoingConcertSeat(
        Long concertId,
        Long userId,
        Long scheduleId,
        Long areaId,
        int seatRow,
        int seatColumn
    );
}
