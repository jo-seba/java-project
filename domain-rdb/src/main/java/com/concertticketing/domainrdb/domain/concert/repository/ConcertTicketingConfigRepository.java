package com.concertticketing.domainrdb.domain.concert.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.concertticketing.domainrdb.domain.concert.domain.ConcertTicketingConfig;

public interface ConcertTicketingConfigRepository extends JpaRepository<ConcertTicketingConfig, Long> {
    @Query("SELECT ctc FROM ConcertTicketingConfig ctc "
        + "WHERE ctc.startedAt < :nextDay AND ctc.endedAt >= :today")
    Optional<ConcertTicketingConfig> findConcertTicketingQueueConfig(LocalDateTime today, LocalDateTime nextDay);
}

