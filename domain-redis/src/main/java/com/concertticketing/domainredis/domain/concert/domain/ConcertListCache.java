package com.concertticketing.domainredis.domain.concert.domain;

import java.time.LocalDateTime;

public record ConcertListCache(
    Long id,
    String title,
    String venueName,
    String thumbnail,
    LocalDateTime startedAt,
    LocalDateTime endedAt
) {
}
