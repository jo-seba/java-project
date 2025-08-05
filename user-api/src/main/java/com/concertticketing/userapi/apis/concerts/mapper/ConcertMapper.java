package com.concertticketing.userapi.apis.concerts.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.concertticketing.domainredis.domain.concert.domain.ConcertTicketingCache;
import com.concertticketing.userapi.apis.concerts.dbdto.ConcertTicketingInfoDBDto;
import com.concertticketing.userapi.apis.concerts.domain.Concert;
import com.concertticketing.userapi.apis.concerts.dto.ConcertDetailDto.ConcertDetailRes;
import com.concertticketing.userapi.apis.venues.domain.VenueArea;
import com.concertticketing.userapi.apis.venues.mapper.VenueMapper;
import com.concertticketing.userapi.common.mapstruct.MapStructBaseConfig;
import com.concertticketing.userapi.common.mapstruct.TimeMapStructHelper;

@Mapper(
    config = MapStructBaseConfig.class,
    uses = {
        TimeMapStructHelper.class,
        ConcertDetailImageMapper.class,
        ConcertConcertCategoryMapper.class,
        VenueMapper.class,
    }
)
public interface ConcertMapper {
    @Mapping(target = "categories", source = "concert.concertCategories", qualifiedByName = "convertConcertConcertCategoryListToStringList")
    ConcertDetailRes toConcertDetailDto(Concert concert, List<VenueArea> areas);

    ConcertTicketingCache toConcertTicketingCache(ConcertTicketingInfoDBDto concert);
}
