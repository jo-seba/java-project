package com.concertticketing.userapi.apis.venues.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public record VenueAreaDto(
    @Schema(description = "영역 이름")
    String name,
    @Schema(description = "요금")
    BigDecimal price
) {
}
