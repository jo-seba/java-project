package com.concertticketing.api.apis.v1.concerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConcertAreaResDto {
    private String name;
    private int price;
}
