package com.concertticketing.domainrdb.domain.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concertticketing.domainrdb.domain.company.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
