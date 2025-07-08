package com.concertticketing.sellerapi.apis.sellers.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.commonerror.exception.common.CommonConflictException;
import com.concertticketing.sellerapi.apis.sellers.domain.Seller;
import com.concertticketing.sellerapi.apis.sellers.repository.SellerRepository;

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
            throw new CommonConflictException();
        }
    }
}
