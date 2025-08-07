package com.concertticketing.userapi.apis.concerts.dbdto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public record ConcertTicketingInfoDBDto(
    Long id,
    LocalDateTime bookingStartedAt,
    LocalDateTime bookingEndedAt,
    Integer capacity
) {
    @QueryProjection
    public ConcertTicketingInfoDBDto {
    }
}
