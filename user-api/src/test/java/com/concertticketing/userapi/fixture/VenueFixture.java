package com.concertticketing.userapi.fixture;

import static com.concertticketing.userapi.fixture.common.RandomNumberData.*;
import static com.concertticketing.userapi.fixture.common.RandomStringData.*;

import com.concertticketing.userapi.apis.venues.domain.Venue;

public final class VenueFixture {
    public static Venue newVenue(Integer id) {
        return new Venue(
            id,
            randomText("venue name"),
            randomPositiveNum(),
            randomText("road address"),
            randomLatitude(),
            randomLongitude()
        );
    }
}
