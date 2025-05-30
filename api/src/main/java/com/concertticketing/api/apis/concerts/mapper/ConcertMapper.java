package com.concertticketing.api.apis.concerts.mapper;

import com.concertticketing.api.apis.concerts.domain.Concert;
import com.concertticketing.api.apis.concerts.dto.ConcertDetailDto.ConcertDetailRes;
import com.concertticketing.api.apis.venues.domain.VenueArea;
import com.concertticketing.api.apis.venues.mapper.VenueMapper;
import com.concertticketing.api.common.mapstruct.MapStructBaseConfig;
import org.mapstruct.Mapper;

import java.util.List;

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
