package com.concertticketing.sellerapi.apis.concerts.dbdto;

public record ConcertDetailImageDBDto(
    Long concertId,
    String imageUrl
) {
}
