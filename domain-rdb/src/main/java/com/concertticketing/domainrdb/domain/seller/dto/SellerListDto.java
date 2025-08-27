package com.concertticketing.domainrdb.domain.seller.dto;

import com.concertticketing.domainrdb.domain.seller.enums.SellerRole;
import com.querydsl.core.annotations.QueryProjection;

public record SellerListDto(
    Integer id,
    String email,
    SellerRole role,
    String name,
    String phoneNumber
) {
    @QueryProjection
    public SellerListDto {
    }
}
