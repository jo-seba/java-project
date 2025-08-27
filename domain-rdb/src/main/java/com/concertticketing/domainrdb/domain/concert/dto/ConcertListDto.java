package com.concertticketing.domainrdb.domain.concert.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public record ConcertListDto(
    Long id,
    String title,
    String venueName,
    String thumbnail,
    LocalDateTime startedAt,
    LocalDateTime endedAt
) {
    @QueryProjection
    public ConcertListDto {
    }
}