package com.concertticketing.api.apis.venues.mapper;

import com.concertticketing.api.apis.venues.domain.Venue;
import com.concertticketing.api.apis.venues.dto.VenueDto;
import com.concertticketing.api.common.mapstruct.MapStructBaseConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructBaseConfig.class)
public interface VenueMapper {
    VenueDto toDto(Venue venue);
}
