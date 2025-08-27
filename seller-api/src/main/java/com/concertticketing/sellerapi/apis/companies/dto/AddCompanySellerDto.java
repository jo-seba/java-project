package com.concertticketing.sellerapi.apis.companies.dto;

import com.concertticketing.domainrdb.domain.seller.enums.SellerRole;
import com.concertticketing.sellerapi.common.validation.OneOfSellerRole;
import com.concertticketing.sellerapi.common.validation.PhoneNumber;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public final class AddCompanySellerDto {
    public record AddCompanySellerBody(
        @NotBlank
        @Email
        @Size(max = 30)
        String email,
        @Schema(description = "역할 설정 MEMBER or MANAGER 만 가능")
        @NotNull
        @OneOfSellerRole(anyOf = {SellerRole.MEMBER, SellerRole.MANAGER})
        SellerRole role,
        @NotBlank
        @Size(max = 24)
        String name,
        @Schema(description = "한국 전화번호 010 등 10~11자리")
        @NotBlank
        @PhoneNumber
        String phoneNumber
    ) {
    }
}
