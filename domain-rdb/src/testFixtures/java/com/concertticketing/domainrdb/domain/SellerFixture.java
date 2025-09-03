package com.concertticketing.domainrdb.domain;

import static com.concertticketing.domainrdb.common.RandomGenerator.*;
import static com.concertticketing.domainrdb.common.ReflectionField.*;

import com.concertticketing.domainrdb.domain.seller.domain.Seller;
import com.concertticketing.domainrdb.domain.seller.dto.SellerListDto;
import com.concertticketing.domainrdb.domain.seller.enums.SellerRole;

import lombok.Builder;

public final class SellerFixture {
    private SellerFixture() {
    }

    @Builder(builderMethodName = "sellerBuilder")
    private static Seller seller(
        Integer id,
        Integer companyId,
        String email,
        String name,
        SellerRole role
    ) {
        Seller seller = new Seller(
            CompanyFixture.builder()
                .id(companyId)
                .build(),
            email != null ? email : randomText("email"),
            role != null ? role : SellerRole.MEMBER,
            name != null ? name : randomText("name"),
            randomPhoneNumber()
        );

        setField(seller, "id", id != null ? id : randomPositiveNum());

        return seller;
    }

    @Builder(builderMethodName = "sellerListDtoBuilder")
    private static SellerListDto sellerListDto(
        Integer id
    ) {
        return new SellerListDto(
            id != null ? id : randomPositiveNum(),
            randomText("email"),
            SellerRole.MEMBER,
            randomText("name"),
            randomPhoneNumber()
        );
    }
}
