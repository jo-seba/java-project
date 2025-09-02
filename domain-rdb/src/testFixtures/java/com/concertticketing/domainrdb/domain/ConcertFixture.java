package com.concertticketing.domainrdb.domain;

import static com.concertticketing.domainrdb.common.RandomGenerator.*;
import static com.concertticketing.domainrdb.common.ReflectionField.*;

import java.time.LocalDateTime;

import com.concertticketing.domainrdb.domain.concert.domain.Concert;
import com.concertticketing.domainrdb.domain.concert.dto.ConcertListDto;

import lombok.Builder;

public class ConcertFixture {
    private ConcertFixture() {
    }

    @Builder(builderMethodName = "concertBuilder")
    private static Concert concert(
        Long id,
        String title,
        String description,
        String thumbnail,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        LocalDateTime bookingStartedAt,
        LocalDateTime bookingEndedAt,
        Integer companyId,
        Integer venueId,
        Long venueLayoutId
    ) {
        Concert concert = new Concert(
            title != null ? title : randomText("title"),
            randomPositiveNum(20, 300),
            description != null ? description : randomText("description"),
            thumbnail != null ? thumbnail : randomUrl(),
            startedAt != null ? startedAt : LocalDateTime.now().plusDays(30),
            endedAt != null ? endedAt : LocalDateTime.now().plusDays(31),
            bookingStartedAt != null ? bookingStartedAt : LocalDateTime.now().plusDays(10),
            bookingEndedAt != null ? bookingEndedAt : LocalDateTime.now().plusDays(13),
            CompanyFixture.builder()
                .id(companyId)
                .build(),
            VenueFixture.builder()
                .id(venueId)
                .build(),
            VenueLayoutFixture.builder()
                .id(venueLayoutId)
                .build()
        );

        setField(concert, "id", id != null ? id : randomPositiveNumLong());

        return concert;
    }

    @Builder(builderMethodName = "concertListDtoBuilder")
    private static ConcertListDto concertListDto(
        Long id
    ) {
        return new ConcertListDto(
            id != null ? id : randomPositiveNumLong(),
            randomText("title"),
            randomText("venueName"),
            randomUrl(),
            LocalDateTime.now().plusDays(30),
            LocalDateTime.now().plusDays(35)
        );
    }
}
