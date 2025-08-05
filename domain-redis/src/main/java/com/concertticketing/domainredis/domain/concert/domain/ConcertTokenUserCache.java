package com.concertticketing.domainredis.domain.concert.domain;

import com.concertticketing.domainredis.common.constant.ConcertEntryStatus;

public record ConcertTokenUserCache(
    Long userId,
    Long concertId,
    ConcertEntryStatus status,
    Long lastWaitingCount
) {
    public static ConcertTokenUserCache of(Long userId, Long concertId, Long lastWaitingCount) {
        return new ConcertTokenUserCache(
            userId,
            concertId,
            ConcertEntryStatus.WAITING,
            lastWaitingCount
        );
    }

    public ConcertTokenUserCache withStatus(ConcertEntryStatus status) {
        return new ConcertTokenUserCache(userId, concertId, status, lastWaitingCount);
    }
}
