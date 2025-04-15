package com.concertticketing.api.apis.v1.concerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConcertListItemResDto {
    private int id;
    private String title;
    private String location;
    private String thumbnail;
    private String startedAt;
    private String endedAt;
    private String bookingStartedAt;
    private String bookingEndedAt;

    public static ConcertListItemResDto fromEntity(int id, String title) {
        return new ConcertListItemResDto(
                id,
                title,
                "서울 어딘가",
                "https://cdn.asdfazxvewa.comage/eafw/123",
                "2025-04-28T08:00:00.000",
                "2025-04-28T10:30:00.000",
                "2025-04-16T08:00:00.000",
                "2025-04-20T08:00:00.000"
        );
    }
}
