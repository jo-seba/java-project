package com.concertticketing.domainrdb.domain;

import static com.concertticketing.domainrdb.common.RandomGenerator.*;
import static com.concertticketing.domainrdb.common.ReflectionField.*;

import com.concertticketing.domainrdb.domain.concert.domain.ConcertCategory;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConcertCategoryFixture {
    @Builder
    private static ConcertCategory concertCategory(
        Integer id,
        String name
    ) {
        ConcertCategory concertCategory = new ConcertCategory(
            name != null ? name : randomText("category")
        );

        setField(concertCategory, "id", id != null ? id : 1);

        return concertCategory;
    }
}
