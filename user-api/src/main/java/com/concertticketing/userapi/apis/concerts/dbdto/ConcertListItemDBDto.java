package com.concertticketing.userapi.apis.concerts.dbdto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.media.Schema;

public record ConcertListItemDBDto(
    @Schema(description = "id")
    Long id,
    @Schema(description = "콘서트 이름")
    String title,
    @Schema(description = "공연장 이름")
    String venueName,
    @Schema(description = "섬네일")
    String thumbnail,
    @Schema(description = "콘서트 시작")
    LocalDateTime startedAt,
    @Schema(description = "콘서트 종료")
    LocalDateTime endedAt
) {
    @QueryProjection
    public ConcertListItemDBDto {
    }
}