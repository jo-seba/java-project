package com.concertticketing.userapi.apis.concerts.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class HoldConcertSeatDto {
    public record HoldConcertSeatReq(
        @NotNull
        @Min(1)
        Long scheduleId,
        @NotNull
        @Min(1)
        Long areaId,
        @NotNull
        @Min(0)
        int seatRow,
        @NotNull
        @Min(0)
        int seatColumn
    ) {
    }

    public record HoldConcertSeatRes(
        boolean isSuccess
    ) {
    }
}
