package com.concertticketing.api.apis.v1.concerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ConcertDetailResDto {
    private int id;
    private String title;
    private String description;
    private String thumbnail;
    private String startedAt;
    private String endedAt;
    private String bookingStartedAt;
    private String bookingEndedAt;
    private List<String> detailImages;
    private ConcertVenueResDto venue;
    private List<ConcertScheduleResDto> schedules;

    public static ConcertDetailResDto fromEntity() {
        return new ConcertDetailResDto(
                1,
                "hi",
                "asdf",
                "https://cdn.asdfazxvewa.comage/eafw/123",
                "2025-04-28T08:00:00.000",
                "2025-04-28T10:30:00.000",
                "2025-04-16T08:00:00.000",
                "2025-04-20T08:00:00.000",
                List.of("https://cdn.asdfazxvewa.comage/eafw/1", "https://cdn.asdfazxvewa.comage/eafw/2"),
                ConcertVenueResDto.fromEntity(),
                List.of(ConcertScheduleResDto.fromEntity())
        );
    }
}
