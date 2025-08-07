package com.concertticketing.sellerapi.apis.concerts.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.util.CollectionUtils;

import com.concertticketing.sellerapi.apis.concerts.dbdto.ConcertDetailImageDBDto;
import com.concertticketing.sellerapi.apis.concerts.domain.ConcertDetailImage;
import com.concertticketing.sellerapi.common.mapstruct.MapStructBaseConfig;

@Mapper(config = MapStructBaseConfig.class)
public interface ConcertDetailImageMapper {
    ConcertDetailImageDBDto toConcertDetailImageDto(Long concertId, String imageUrl);

    default List<ConcertDetailImageDBDto> toConcertDetailImageDtos(Long concertId, List<String> imageUrls) {
        if (CollectionUtils.isEmpty(imageUrls)) {
            return new ArrayList<>();
        }
        return imageUrls.stream()
            .map(imageUrl -> toConcertDetailImageDto(concertId, imageUrl))
            .collect(Collectors.toList());
    }

    default String toImageUrl(ConcertDetailImage concertDetailImage) {
        return concertDetailImage != null ? concertDetailImage.getImageUrl() : "";
    }
}
