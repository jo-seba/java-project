package com.concertticketing.schedulercore.domain.concert.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.concertticketing.schedulercore.domain.concert.domain.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    /**
     * date 기준으로 예약 가능한 콘서트 추출
     * @param nextDay 당일 + 1
     * @param today 당일
     */
    @Query("SELECT c FROM Concert c "
        + "WHERE c.bookingStartedAt < :nextDay AND c.bookingEndedAt >= :today")
    List<Concert> findBookableConcerts(LocalDateTime today, LocalDateTime nextDay);
}
