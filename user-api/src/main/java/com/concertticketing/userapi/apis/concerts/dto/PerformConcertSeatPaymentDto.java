package com.concertticketing.userapi.apis.concerts.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PerformConcertSeatPaymentDto {
    public record PerformConcertSeatPaymentReq(
        @NotBlank
        String billingKey,
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

    public record PerformConcertSeatPaymentRes(
        boolean isSuccess
    ) {
    }
}
