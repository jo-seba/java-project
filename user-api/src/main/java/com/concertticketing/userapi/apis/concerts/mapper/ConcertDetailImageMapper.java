package com.concertticketing.userapi.apis.concerts.mapper;

import org.mapstruct.Mapper;

import com.concertticketing.userapi.apis.concerts.domain.ConcertDetailImage;
import com.concertticketing.userapi.common.mapstruct.MapStructBaseConfig;

@Mapper(config = MapStructBaseConfig.class)
public interface ConcertDetailImageMapper {
    default String toImageUrl(ConcertDetailImage concertDetailImage) {
        return concertDetailImage != null ? concertDetailImage.getImageUrl() : "";
    }
}
