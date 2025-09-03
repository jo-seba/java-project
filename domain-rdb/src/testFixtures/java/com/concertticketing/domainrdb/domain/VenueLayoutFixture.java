package com.concertticketing.domainrdb.domain;

import static com.concertticketing.domainrdb.common.RandomGenerator.*;
import static com.concertticketing.domainrdb.common.ReflectionField.*;

import com.concertticketing.domainrdb.domain.venue.domain.VenueLayout;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VenueLayoutFixture {
    @Builder
    private static VenueLayout venueLayout(
        Long id
    ) {
        VenueLayout venueLayout = new VenueLayout(
            randomText("venueLayout")
        );

        setField(venueLayout, "id", id != null ? id : randomPositiveNum());

        return venueLayout;
    }
}
