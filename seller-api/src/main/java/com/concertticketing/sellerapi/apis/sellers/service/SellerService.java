package com.concertticketing.sellerapi.apis.sellers.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.commonerror.exception.common.CommonConflictException;
import com.concertticketing.commonerror.exception.common.CommonNotFoundException;
import com.concertticketing.sellerapi.apis.companies.constant.CompanySellerSort;
import com.concertticketing.sellerapi.apis.companies.dto.CompanySellerListDto;
import com.concertticketing.sellerapi.apis.sellers.constant.SellerRole;
import com.concertticketing.sellerapi.apis.sellers.domain.Seller;
import com.concertticketing.sellerapi.apis.sellers.repository.SellerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;

    // Create

    /**
     * @param seller the seller to save
     * @return the saved seller
     * @throws CommonConflictException duplicate key or unique key error
     */
    @Transactional
    public Seller saveSeller(Seller seller) {
        try {
            return sellerRepository.save(seller);
        } catch (DataIntegrityViolationException e) {
            throw new CommonConflictException();
        }
    }

    // Read
    @Transactional(readOnly = true)
    public Seller findSeller(Integer id) {
        return sellerRepository.findById(id).orElseThrow(CommonNotFoundException::new);
    }

    @Transactional(readOnly = true)
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

    // Update
    @Transactional
    public int updateSeller(Integer id, Integer companyId, SellerRole role, String name, String phoneNumber) {
        return sellerRepository.updateSeller(id, companyId, role, name, phoneNumber);
    }

    // Delete
    @Transactional
    public void deleteSeller(Integer id, Integer companyId) {
        sellerRepository.deleteSellerByIdAndCompanyId(id, companyId);
    }
}
