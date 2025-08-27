package com.concertticketing.userapi.common.converter;

import org.springframework.core.convert.converter.Converter;

import com.concertticketing.domainrdb.domain.concert.enums.ConcertSort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcertSortConverter implements Converter<String, ConcertSort> {
    @Override
    public ConcertSort convert(String source) {
        try {
            return ConcertSort.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid value: " + source);
        }
    }
}
