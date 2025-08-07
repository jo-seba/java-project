package com.concertticketing.sellerapi.apis.companies.dto;

import java.util.List;

import com.concertticketing.sellerapi.apis.companies.constant.CompanySellerSort;
import com.concertticketing.sellerapi.apis.sellers.constant.SellerRole;
import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

public final class CompanySellerListDto {
    public record CompanySellerListQuery(
        @Schema(description = "정렬 기준", example = "NEWEST", defaultValue = "NEWEST", implementation = CompanySellerSort.class)
        CompanySellerSort sort,
        @Schema(description = "page >= 1", example = "1")
        @Min(1)
        int page
    ) {
        @Override
        public CompanySellerSort sort() {
            return sort != null ? sort : CompanySellerSort.NEWEST;
        }

        @Schema(hidden = true)
        public int getPageablePage() {
            return page - 1;
        }
    }

    public record CompanySellerListRes(
        int currentPage,
        int totalPage,
        List<CompanySellerListItem> sellers
    ) {
    }

    public record CompanySellerListItem(
        Integer id,
        String email,
        SellerRole role,
        String name,
        String phoneNumber
    ) {
        @QueryProjection
        public CompanySellerListItem {
        }
    }
}
