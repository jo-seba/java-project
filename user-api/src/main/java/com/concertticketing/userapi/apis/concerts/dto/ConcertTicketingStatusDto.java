package com.concertticketing.userapi.apis.concerts.dto;

import com.concertticketing.domainredis.common.constant.ConcertEntryStatus;

public final class ConcertTicketingStatusDto {
    public record ConcertTicketingStatusRes(
        ConcertEntryStatus status,
        Long remainingWaitingCount
    ) {
    }
}
