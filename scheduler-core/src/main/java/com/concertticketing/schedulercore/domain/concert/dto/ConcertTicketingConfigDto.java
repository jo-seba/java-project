package com.concertticketing.schedulercore.domain.concert.dto;

import java.time.LocalDateTime;

public record ConcertTicketingConfigDto(
    Long id,
    LocalDateTime startedAt,
    LocalDateTime endedAt,
    Integer capacity
) {
}
