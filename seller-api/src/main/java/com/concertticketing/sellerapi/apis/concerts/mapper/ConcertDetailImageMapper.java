package com.concertticketing.sellerapi.apis.concerts.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.util.CollectionUtils;

import com.concertticketing.domainrdb.domain.concert.domain.ConcertDetailImage;
import com.concertticketing.domainrdb.domain.concert.dto.ConcertDetailImageDto;
import com.concertticketing.sellerapi.common.mapstruct.MapStructBaseConfig;

@Mapper(config = MapStructBaseConfig.class)
public interface ConcertDetailImageMapper {
    ConcertDetailImageDto toConcertDetailImageDto(Long concertId, String imageUrl);

    default List<ConcertDetailImageDto> toConcertDetailImageDtos(Long concertId, List<String> imageUrls) {
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
