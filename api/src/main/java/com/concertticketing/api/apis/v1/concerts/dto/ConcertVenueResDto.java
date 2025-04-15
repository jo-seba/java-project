package com.concertticketing.api.apis.v1.concerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ConcertVenueResDto {
    private String name;
    private String location;
    private int capacity;
    private List<ConcertAreaResDto> areas;

    public static ConcertVenueResDto fromEntity() {
        return new ConcertVenueResDto(
                "venue",
                "서울특별시",
                500,
                List.of(
                        new ConcertAreaResDto(
                                "A석",
                                100000
                        ),
                        new ConcertAreaResDto(
                                "B석",
                                80000
                        )
                )
        );
    }
}
