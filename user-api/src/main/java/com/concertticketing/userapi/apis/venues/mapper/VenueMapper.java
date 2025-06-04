package com.concertticketing.userapi.apis.venues.mapper;

import org.mapstruct.Mapper;

import com.concertticketing.userapi.apis.venues.domain.Venue;
import com.concertticketing.userapi.apis.venues.dto.VenueDto;
import com.concertticketing.userapi.common.mapstruct.MapStructBaseConfig;

@Mapper(config = MapStructBaseConfig.class)
public interface VenueMapper {
    VenueDto toDto(Venue venue);
}
