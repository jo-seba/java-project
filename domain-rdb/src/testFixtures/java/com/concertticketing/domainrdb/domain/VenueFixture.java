package com.concertticketing.domainrdb.domain;

import static com.concertticketing.domainrdb.common.RandomGenerator.*;
import static com.concertticketing.domainrdb.common.ReflectionField.*;

import com.concertticketing.domainrdb.domain.venue.domain.Venue;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VenueFixture {
    @Builder
    private static Venue venue(
        Integer id
    ) {
        Venue venue = new Venue(
            randomText("venue"),
            randomPositiveNum(100, 10000),
            randomText("roadAddress"),
            randomLatitude(),
            randomLongitude()
        );

        setField(venue, "id", id != null ? id : randomPositiveNum());

        return venue;
    }
}
