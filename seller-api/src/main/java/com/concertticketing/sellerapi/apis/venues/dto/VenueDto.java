package com.concertticketing.sellerapi.apis.venues.dto;

import java.math.BigDecimal;

public record VenueDto(
    String name,
    int capacity,
    String roadAddress,
    BigDecimal latitude,
    BigDecimal longitude
) {
}
