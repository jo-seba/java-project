package com.concertticketing.domainrdb.domain.seller.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.concertticketing.domainrdb.domain.seller.dto.SellerListDto;
import com.concertticketing.domainrdb.domain.seller.enums.SellerSort;

public interface SellerRepositoryCustom {
    Page<SellerListDto> findSellersDto(
        Integer companyId,
        SellerSort sort,
        Pageable pageable
    );
}
