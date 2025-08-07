package com.concertticketing.schedulercore.domain.concert.dto;

import java.time.LocalDateTime;

public record BookableConcertDto(
    Long id,
    LocalDateTime bookingStartedAt,
    LocalDateTime bookingEndedAt
) {
}
