package com.concertticketing.sellerapi.apis.concerts.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import com.concertticketing.sellerapi.apis.concerts.dbdto.ConcertConcertCategoryDBDto;
import com.concertticketing.sellerapi.apis.concerts.domain.ConcertConcertCategory;
import com.concertticketing.sellerapi.apis.concerts.dto.ConcertCategoryDto;
import com.concertticketing.sellerapi.common.mapstruct.MapStructBaseConfig;

@Mapper(
    config = MapStructBaseConfig.class
)
public interface ConcertConcertCategoryMapper {
    ConcertConcertCategoryDBDto toConcertConcertCategoryDto(Long concertId, Integer categoryId);

    @Mapping(target = ".", source = "concertConcertCategory.category")
    ConcertCategoryDto toConcertCategoryDto(ConcertConcertCategory concertConcertCategory);

    default List<ConcertConcertCategoryDBDto> toConcertConcertCategoryDtos(Long concertId, List<Integer> categoryIds) {
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
