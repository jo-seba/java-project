package com.concertticketing.domainrdb.domain;

import java.util.Objects;

import com.concertticketing.domainrdb.domain.concert.domain.Concert;
import com.concertticketing.domainrdb.domain.concert.domain.ConcertCategory;
import com.concertticketing.domainrdb.domain.concert.domain.ConcertConcertCategory;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConcertConcertCategoryFixture {
    @Builder
    private static ConcertConcertCategory concertConcertCategory(
        Concert concert,
        ConcertCategory concertCategory
    ) {
        Objects.requireNonNull(concert);
        return new ConcertConcertCategory(
            concert,
            concertCategory != null ? concertCategory : ConcertCategoryFixture.builder().build()
        );
    }
}
