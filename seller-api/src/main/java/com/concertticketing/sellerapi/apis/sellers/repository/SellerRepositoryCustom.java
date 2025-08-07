package com.concertticketing.sellerapi.apis.sellers.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.concertticketing.sellerapi.apis.companies.constant.CompanySellerSort;
import com.concertticketing.sellerapi.apis.companies.dto.CompanySellerListDto;

public interface SellerRepositoryCustom {
    Page<CompanySellerListDto.CompanySellerListItem> findSellersDto(
        Integer companyId,
        CompanySellerSort sort,
        Pageable pageable
    );
}
