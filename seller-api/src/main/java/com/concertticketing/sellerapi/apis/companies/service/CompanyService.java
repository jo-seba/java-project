package com.concertticketing.sellerapi.apis.companies.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.domainrdb.domain.company.domain.Company;
import com.concertticketing.domainrdb.domain.company.repository.CompanyRepository;

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
