package com.concertticketing.api.common.converter;

import com.concertticketing.api.apis.concerts.constant.ConcertSort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class ConcertSortConverter implements Converter<String, ConcertSort> {
    @Override
    public ConcertSort convert(String source) {
        log.info("convert concert sort to ConcertSort {}", source);
        try {
            return ConcertSort.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid value: " + source);
        }
    }
}
