package com.concertticketing.domainrdb.domain;

import static com.concertticketing.domainrdb.common.RandomGenerator.*;
import static com.concertticketing.domainrdb.common.ReflectionField.*;

import com.concertticketing.domainrdb.domain.concert.domain.ConcertDetailImage;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConcertDetailImageFixture {
    @Builder
    private static ConcertDetailImage concertDetailImage(
        Long id,
        String imageUrl
    ) {
        ConcertDetailImage concertDetailImage = new ConcertDetailImage(
            imageUrl != null ? imageUrl : randomUrl()
        );

        setField(concertDetailImage, "id", id != null ? id : randomPositiveNumLong());

        return concertDetailImage;
    }
}
