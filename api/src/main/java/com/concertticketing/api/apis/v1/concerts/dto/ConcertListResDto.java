package com.concertticketing.api.apis.v1.concerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ConcertListResDto {
    private List<ConcertListItemResDto> concerts;

    public static ConcertListResDto fromEntity() {
        return new ConcertListResDto(
                List.of(
                        ConcertListItemResDto.fromEntity(1, "asdf"),
                        ConcertListItemResDto.fromEntity(2, "zzzf")
                )
        );
    }
}
