package com.concertticketing.domainrdb.domain.concert.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public record ConcertTicketingInfoDto(
    Long id,
    LocalDateTime bookingStartedAt,
    LocalDateTime bookingEndedAt,
    Integer capacity
) {
    @QueryProjection
    public ConcertTicketingInfoDto {
    }
}
