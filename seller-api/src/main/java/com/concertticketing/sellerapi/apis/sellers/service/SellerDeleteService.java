package com.concertticketing.sellerapi.apis.sellers.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.sellers.repository.SellerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerDeleteService {
    private final SellerRepository sellerRepository;

    public void delete(Integer id, Integer companyId) {
        sellerRepository.deleteSellerByIdAndCompanyId(id, companyId);
    }
}
