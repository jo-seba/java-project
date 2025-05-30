package com.concertticketing.api.apis.venues.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record VenueDto(
        @Schema(description = "공연장 이름")
        String name,
        @Schema(description = "수용 인원")
        int capacity,
        @Schema(description = "주소")
        String roadAddress,
        @Schema(description = "위도")
        BigDecimal latitude,
        @Schema(description = "경도")
        BigDecimal longitude
) {}
