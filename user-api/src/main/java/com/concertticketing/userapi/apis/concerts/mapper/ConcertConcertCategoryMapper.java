package com.concertticketing.userapi.apis.concerts.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import com.concertticketing.userapi.apis.concerts.domain.ConcertCategory;
import com.concertticketing.userapi.apis.concerts.domain.ConcertConcertCategory;
import com.concertticketing.userapi.common.mapstruct.MapStructBaseConfig;

@Mapper(
    config = MapStructBaseConfig.class
)
public interface ConcertConcertCategoryMapper {
    @Named("convertConcertConcertCategoryListToStringList")
    default List<String> convertConcertConcertCategoryListToStringList(List<ConcertConcertCategory> contents) {
        if (CollectionUtils.isEmpty(contents)) {
            return new ArrayList<>();
        }
        return contents.stream()
            .map(ConcertConcertCategory::getCategory)
            .map(ConcertCategory::getName)
            .toList();
    }
}
