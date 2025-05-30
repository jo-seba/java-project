package com.concertticketing.api.apis.venues.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record VenueAreaDto(
        @Schema(description = "영역 이름")
        String name,
        @Schema(description = "요금")
        BigDecimal price
) {}
