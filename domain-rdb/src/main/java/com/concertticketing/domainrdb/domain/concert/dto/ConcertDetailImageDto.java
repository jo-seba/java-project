package com.concertticketing.domainrdb.domain.concert.dto;

public record ConcertDetailImageDto(
    Long concertId,
    String imageUrl
) {
}
