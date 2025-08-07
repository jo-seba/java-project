package com.concertticketing.sellerapi.apis.companies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concertticketing.sellerapi.apis.companies.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
