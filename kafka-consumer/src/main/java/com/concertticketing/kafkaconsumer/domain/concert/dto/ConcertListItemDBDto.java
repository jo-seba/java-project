package com.concertticketing.kafkaconsumer.domain.concert.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public record ConcertListItemDBDto(
    Long id,
    String title,
    String venueName,
    String thumbnail,
    LocalDateTime startedAt,
    LocalDateTime endedAt
) {
    @QueryProjection
    public ConcertListItemDBDto {
    }
}
