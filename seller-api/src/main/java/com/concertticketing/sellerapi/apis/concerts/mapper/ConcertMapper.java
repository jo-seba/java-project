package com.concertticketing.sellerapi.apis.concerts.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.concertticketing.domainrdb.domain.company.domain.Company;
import com.concertticketing.domainrdb.domain.concert.domain.Concert;
import com.concertticketing.domainrdb.domain.venue.domain.Venue;
import com.concertticketing.domainrdb.domain.venue.domain.VenueLayout;
import com.concertticketing.sellerapi.apis.concerts.dto.AddConcertDto.AddConcertBody;
import com.concertticketing.sellerapi.apis.concerts.dto.ConcertDetailDto.ConcertDetailRes;
import com.concertticketing.sellerapi.apis.concerts.dto.ConcertListDto.ConcertListItem;
import com.concertticketing.sellerapi.common.mapstruct.MapStructBaseConfig;

@Mapper(
    config = MapStructBaseConfig.class,
    uses = {
        ConcertDetailImageMapper.class,
        ConcertConcertCategoryMapper.class
    }
)
public interface ConcertMapper {
    @Mapping(target = "concertCategories", ignore = true)
    @Mapping(target = "detailImages", ignore = true)
    Concert toConcert(
        AddConcertBody body,
        Company company,
        Venue venue,
        VenueLayout venueLayout
    );

    @Mapping(target = "categories", source = "concertCategories")
    ConcertDetailRes toConcertDetailDto(Concert concert);

    @Mapping(target = "venueName", source = "venue.name")
    @Mapping(target = "categories", source = "concert.concertCategories", qualifiedByName = "convertConcertConcertCategoryListToStringList")
    ConcertListItem toConcertListItem(Concert concert);

    List<ConcertListItem> toConcertListItems(List<Concert> concerts);
}
