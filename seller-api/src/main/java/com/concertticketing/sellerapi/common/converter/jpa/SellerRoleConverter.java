package com.concertticketing.sellerapi.common.converter.jpa;

import com.concertticketing.sellerapi.apis.sellers.constant.SellerRole;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SellerRoleConverter implements AttributeConverter<SellerRole, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SellerRole sellerRole) {
        if (sellerRole == null) {
            return null;
        }
        return sellerRole.getDbValue();
    }

    @Override
    public SellerRole convertToEntityAttribute(Integer dbValue) {
        if (dbValue == null) {
            throw new IllegalArgumentException("invalid dbValue: is null");
        }
        return SellerRole.from(dbValue);
    }
}
