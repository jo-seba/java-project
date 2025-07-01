package com.concertticketing.sellerapi.apis.companies.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.companies.domain.Company;
import com.concertticketing.sellerapi.apis.companies.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanySearchService {
    private final CompanyRepository companyRepository;

    public Company getReference(Integer id) {
        return companyRepository.getReferenceById(id);
    }
}
