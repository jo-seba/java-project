package com.concertticketing.domainrdb.domain;

import static com.concertticketing.domainrdb.common.RandomGenerator.*;
import static com.concertticketing.domainrdb.common.ReflectionField.*;

import com.concertticketing.domainrdb.domain.venue.domain.VenueArea;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VenueAreaFixture {
    @Builder
    private static VenueArea venueArea(
        Integer id,
        Long layoutId
    ) {
        VenueArea venueArea = new VenueArea(
            layoutId
        );

        setField(venueArea, "id", id != null ? id : randomPositiveNum());

        return venueArea;
    }
}
