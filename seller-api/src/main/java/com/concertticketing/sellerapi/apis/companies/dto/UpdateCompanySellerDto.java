package com.concertticketing.sellerapi.apis.companies.dto;

import com.concertticketing.sellerapi.apis.sellers.constant.SellerRole;
import com.concertticketing.sellerapi.common.validation.PhoneNumber;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public final class UpdateCompanySellerDto {
    public record UpdateCompanySellerBody(
        @Schema(description = "본인 아닌 경우만 역할 설정 가능하며, MEMBER or MANAGER 만 가능")
        @NotNull
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
