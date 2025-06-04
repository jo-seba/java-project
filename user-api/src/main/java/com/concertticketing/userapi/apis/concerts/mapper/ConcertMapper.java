package com.concertticketing.userapi.apis.concerts.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.concertticketing.userapi.apis.concerts.domain.Concert;
import com.concertticketing.userapi.apis.concerts.dto.ConcertDetailDto.ConcertDetailRes;
import com.concertticketing.userapi.apis.venues.domain.VenueArea;
import com.concertticketing.userapi.apis.venues.mapper.VenueMapper;
import com.concertticketing.userapi.common.mapstruct.MapStructBaseConfig;

@Mapper(
    config = MapStructBaseConfig.class,
    uses = {
        ConcertDetailImageMapper.class,
        VenueMapper.class,
    }
)
public interface ConcertMapper {
    ConcertDetailRes toDetailDto(Concert concert, List<VenueArea> areas);
}
