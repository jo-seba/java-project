package com.concertticketing.sellerapi.apis.concerts.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.concertticketing.sellerapi.apis.venues.dto.VenueDto;
import com.concertticketing.sellerapi.apis.venues.dto.VenueLayoutDto;

public final class ConcertDetailDto {
    public record ConcertDetailRes(
        Long id,
        String title,
        Integer duration,
        Long viewCount,
        String description,
        String thumbnail,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        LocalDateTime bookingStartedAt,
        LocalDateTime bookingEndedAt,
        VenueDto venue,
        VenueLayoutDto venueLayout,
        List<String> detailImages,
        List<ConcertCategoryDto> categories
    ) {
    }
}
