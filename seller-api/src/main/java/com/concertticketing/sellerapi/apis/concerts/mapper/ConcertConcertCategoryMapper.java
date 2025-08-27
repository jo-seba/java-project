package com.concertticketing.sellerapi.apis.concerts.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import com.concertticketing.domainrdb.domain.concert.domain.ConcertConcertCategory;
import com.concertticketing.domainrdb.domain.concert.dto.ConcertConcertCategoryDto;
import com.concertticketing.sellerapi.apis.concerts.dto.ConcertCategoryDto;
import com.concertticketing.sellerapi.common.mapstruct.MapStructBaseConfig;

@Mapper(
    config = MapStructBaseConfig.class
)
public interface ConcertConcertCategoryMapper {
    ConcertConcertCategoryDto toConcertConcertCategoryDto(Long concertId, Integer categoryId);

    @Mapping(target = ".", source = "concertConcertCategory.category")
    ConcertCategoryDto toConcertCategoryDto(ConcertConcertCategory concertConcertCategory);

    default List<ConcertConcertCategoryDto> toConcertConcertCategoryDtos(Long concertId, List<Integer> categoryIds) {
        if (CollectionUtils.isEmpty(categoryIds)) {
            return new ArrayList<>();
        }
        return categoryIds.stream()
            .map(categoryId -> toConcertConcertCategoryDto(concertId, categoryId))
            .collect(Collectors.toList());
    }

    @Named("convertConcertConcertCategoryListToStringList")
    default List<String> convertConcertConcertCategoryListToStringList(List<ConcertConcertCategory> contents) {
        if (CollectionUtils.isEmpty(contents)) {
            return new ArrayList<>();
        }
        return contents.stream()
            .map(content -> content.getCategory().getName())
            .toList();
    }
}
