package com.concertticketing.userapi.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import com.concertticketing.userapi.apis.concerts.mapper.ConcertConcertCategoryMapperImpl;
import com.concertticketing.userapi.apis.concerts.mapper.ConcertDetailImageMapperImpl;
import com.concertticketing.userapi.apis.concerts.mapper.ConcertMapperImpl;
import com.concertticketing.userapi.apis.venues.mapper.VenueMapperImpl;
import com.concertticketing.userapi.common.mapstruct.TimeMapStructHelper;

@TestConfiguration
@Import({
    ConcertMapperImpl.class,
    ConcertDetailImageMapperImpl.class,
    ConcertConcertCategoryMapperImpl.class,
    VenueMapperImpl.class,
    TimeMapStructHelper.class,
})
public class TestMapperConfig {
}
