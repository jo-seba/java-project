package com.concertticketing.api.apis.concerts.mapper;

import com.concertticketing.api.apis.concerts.domain.ConcertDetailImage;
import com.concertticketing.api.common.mapstruct.MapStructBaseConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructBaseConfig.class)
public interface ConcertDetailImageMapper {
    default String toImageUrl(ConcertDetailImage concertDetailImage) {
        return concertDetailImage != null ? concertDetailImage.getImageUrl() : "";
    }
}
