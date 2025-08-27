package com.concertticketing.kafkaconsumer.domain.concert.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.concertticketing.kafkaconsumer.common.constant.SeatStatus;
import com.concertticketing.kafkaconsumer.domain.concert.domain.ConcertSeat;

public interface ConcertSeatRepository extends JpaRepository<ConcertSeat, Long> {
    @Query("SELECT cs FROM ConcertSeat cs "
        + "WHERE cs.concertId = :concertId "
        + "AND cs.scheduleId = :scheduleId "
        + "AND cs.areaId = :areaId "
        + "AND cs.seatRow = :seatRow "
        + "AND cs.seatColumn = :seatColumn "
        + "AND cs.holdUserId = :userId ")
    Optional<ConcertSeat> findConcert(
        Long concertId,
        Long userId,
        Long scheduleId,
        Long areaId,
        int seatRow,
        int seatColumn
    );

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ConcertSeat cs SET cs.status = :status WHERE cs.id = :id")
    long updateConcertSeatStatus(Long id, SeatStatus status);
}
