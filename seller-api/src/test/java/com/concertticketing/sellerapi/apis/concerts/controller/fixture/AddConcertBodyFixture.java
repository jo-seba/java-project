package com.concertticketing.sellerapi.apis.concerts.controller.fixture;

import static com.concertticketing.commonutils.TimeUtils.*;

import java.time.LocalDateTime;
import java.util.List;

import com.concertticketing.sellerapi.apis.concerts.dto.AddConcertDto.AddConcertBody;

import lombok.Builder;
import lombok.Getter;

public class AddConcertBodyFixture {
    private AddConcertBodyFixture() {
    }

    private static final int VENUE_ID = 1;
    private static final long VENUE_LAYOUT_ID = 2L;
    private static final String TITLE = "title";
    private static final int DURATION = 120;
    private static final String DESCRIPTION = "desc";
    private static final String THUMBNAIL = "thumb";
    @Getter
    private static final LocalDateTime STARTED_AT = toLocalDateTime("2026-07-01T10:00:00Z");
    @Getter
    private static final LocalDateTime ENDED_AT = toLocalDateTime("2026-07-06T12:00:00Z");
    @Getter
    private static final LocalDateTime BOOKING_STARTED_AT = toLocalDateTime("2026-06-25T10:00:00Z");
    @Getter
    private static final LocalDateTime BOOKING_ENDED_AT = toLocalDateTime("2026-06-30T12:00:00Z");
    private static final List<Integer> CATEGORY_IDS = List.of(1, 2);
    private static final List<String> DETAIL_IMAGES = List.of(
        "https://example.com/image.png",
        "https://example.com/image2.png"
    );

    @Builder
    private static AddConcertBody addConcertBody(
        String title,
        String description,
        String thumbnail,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        LocalDateTime bookingStartedAt,
        LocalDateTime bookingEndedAt,
        List<Integer> categoryIds,
        List<String> detailImages
    ) {
        return new AddConcertBody(
            VENUE_ID,
            VENUE_LAYOUT_ID,
            title != null ? title : TITLE,
            DURATION,
            description != null ? description : DESCRIPTION,
            thumbnail != null ? thumbnail : THUMBNAIL,
            startedAt != null ? startedAt : STARTED_AT,
            endedAt != null ? endedAt : ENDED_AT,
            bookingStartedAt != null ? bookingStartedAt : BOOKING_STARTED_AT,
            bookingEndedAt != null ? bookingEndedAt : BOOKING_ENDED_AT,
            categoryIds != null ? categoryIds : CATEGORY_IDS,
            detailImages != null ? detailImages : DETAIL_IMAGES
        );
    }
}
