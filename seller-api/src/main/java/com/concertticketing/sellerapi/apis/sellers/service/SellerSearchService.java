package com.concertticketing.sellerapi.apis.sellers.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.companies.constant.CompanySellerSort;
import com.concertticketing.sellerapi.apis.companies.dto.CompanySellerListDto;
import com.concertticketing.sellerapi.apis.sellers.domain.Seller;
import com.concertticketing.sellerapi.apis.sellers.repository.SellerRepository;
import com.concertticketing.sellerapi.common.exception.CommonErrorCode;
import com.concertticketing.sellerapi.common.exception.GlobalErrorException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SellerSearchService {
    private final SellerRepository sellerRepository;

    public Seller find(Integer id) {
        return sellerRepository.findById(id).orElseThrow(() -> new GlobalErrorException(CommonErrorCode.NOT_FOUND));
    }

    // querydsl
    public Page<CompanySellerListDto.CompanySellerListItem> findSellers(
        Integer companyId,
        CompanySellerSort sort,
        Pageable pageable
    ) {
        return sellerRepository.findSellersDto(
            companyId,
            sort,
            pageable
        );
    }
}
