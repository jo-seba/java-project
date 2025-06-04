package com.concertticketing.userapi.apis.concerts.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public record ConcertScheduleDto(
    @Schema(description = "id")
    int id,
    @Schema(description = "콘서트 당일 시각")
    LocalDateTime concertDate,
    @Schema(description = "콘서트 당일 시작 시각")
    LocalDateTime startedAt,
    @Schema(description = "콘서트 당일 종료 시각")
    LocalDateTime endedAt
) {
}
