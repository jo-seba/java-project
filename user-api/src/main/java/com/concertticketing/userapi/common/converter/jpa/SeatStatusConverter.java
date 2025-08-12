package com.concertticketing.userapi.common.converter.jpa;

import com.concertticketing.userapi.apis.concerts.constant.SeatStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SeatStatusConverter implements AttributeConverter<SeatStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SeatStatus seatStatus) {
        if (seatStatus == null) {
            return null;
        }
        return seatStatus.getDbValue();
    }

    @Override
    public SeatStatus convertToEntityAttribute(Integer dbValue) {
        if (dbValue == null) {
            throw new IllegalArgumentException("invalid dbValue: is null");
        }
        return SeatStatus.from(dbValue);
    }
}
