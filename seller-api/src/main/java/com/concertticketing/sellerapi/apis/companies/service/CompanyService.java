package com.concertticketing.sellerapi.apis.companies.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.companies.domain.Company;
import com.concertticketing.sellerapi.apis.companies.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public Company getCompanyReference(Integer id) {
        return companyRepository.getReferenceById(id);
    }
}
