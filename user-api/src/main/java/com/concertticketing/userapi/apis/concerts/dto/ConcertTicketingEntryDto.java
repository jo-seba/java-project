package com.concertticketing.userapi.apis.concerts.dto;

public final class ConcertTicketingEntryDto {
    public record ConcertTicketingEntryRes(
        String token,
        boolean isQueueExclusive
    ) {
    }
}
