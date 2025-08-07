package com.concertticketing.sellerapi.apis.concerts.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Min;

public final class ConcertListDto {
    public record ConcertListQuery(
        @Min(1)
        int page
    ) {
        public int getPageablePage() {
            return page - 1;
        }
    }

    public record ConcertListRes(
        int currentPage,
        int totalPage,
        List<ConcertListItem> concerts
    ) {
    }

    public record ConcertListItem(
        Long id,
        String title,
        String venueName,
        String thumbnail,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        LocalDateTime bookingStartedAt,
        LocalDateTime bookingEndedAt,
        List<String> categories
    ) {
    }
}