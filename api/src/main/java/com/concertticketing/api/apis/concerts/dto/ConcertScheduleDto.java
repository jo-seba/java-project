package com.concertticketing.api.apis.concerts.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ConcertScheduleDto(
        @Schema(description = "id")
        int id,
        @Schema(description = "콘서트 당일 시각")
        LocalDateTime concertDate,
        @Schema(description = "콘서트 당일 시작 시각")
        LocalDateTime startedAt,
        @Schema(description = "콘서트 당일 종료 시각")
        LocalDateTime endedAt
) {}
