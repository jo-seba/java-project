package com.concertticketing.sellerapi.apis.companies.dto;

import java.util.List;

import com.concertticketing.domainrdb.domain.seller.dto.SellerListDto;
import com.concertticketing.domainrdb.domain.seller.enums.SellerSort;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

public final class CompanySellersDto {
    public record CompanySellerListQuery(
        @Schema(description = "정렬 기준", example = "NEWEST", defaultValue = "NEWEST", implementation = SellerSort.class)
        SellerSort sort,
        @Schema(description = "page >= 1", example = "1")
        @Min(1)
        int page
    ) {
        @Override
        public SellerSort sort() {
            return sort != null ? sort : SellerSort.NEWEST;
        }

        @Schema(hidden = true)
        public int getPageablePage() {
            return page - 1;
        }
    }

    public record CompanySellerListRes(
        int currentPage,
        int totalPage,
        List<SellerListDto> sellers
    ) {
    }
}
