package com.concertticketing.sellerapi.apis.sellers.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.sellers.domain.Seller;
import com.concertticketing.sellerapi.apis.sellers.repository.SellerRepository;
import com.concertticketing.sellerapi.common.exception.CommonErrorCode;
import com.concertticketing.sellerapi.common.exception.GlobalErrorException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerCreateService {
    private final SellerRepository sellerRepository;

    public Seller save(Seller seller) {
        try {
            return sellerRepository.save(seller);
        } catch (DataIntegrityViolationException e) {
            throw new GlobalErrorException(CommonErrorCode.CONFLICT);
        }
    }
}
