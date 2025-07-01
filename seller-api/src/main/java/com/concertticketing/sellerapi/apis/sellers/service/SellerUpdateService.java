package com.concertticketing.sellerapi.apis.sellers.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.sellers.constant.SellerRole;
import com.concertticketing.sellerapi.apis.sellers.repository.SellerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerUpdateService {
    private final SellerRepository sellerRepository;

    public int update(Integer id, Integer companyId, SellerRole role, String name, String phoneNumber) {
        return sellerRepository.updateSeller(id, companyId, role, name, phoneNumber);
    }
}
