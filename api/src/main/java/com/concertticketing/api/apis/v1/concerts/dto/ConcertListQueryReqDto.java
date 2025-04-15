package com.concertticketing.api.apis.v1.concerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConcertListQueryReqDto {
    private String sortBy;

    private int page;
}
