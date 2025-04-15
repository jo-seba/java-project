package com.concertticketing.api.apis.v1.concerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConcertScheduleResDto {
    private int id;
    private String concertDate;
    private String startedAt;
    private String endedAt;

    public static ConcertScheduleResDto fromEntity() {
        return new ConcertScheduleResDto(
                2,
                "2025-04-15T00:00:00.000",
                "2025-04-16T08:00:00.000",
                "2025-04-20T10:00:00.000"
        );
    }
}
