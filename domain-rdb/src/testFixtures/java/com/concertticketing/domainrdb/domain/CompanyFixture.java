package com.concertticketing.domainrdb.domain;

import static com.concertticketing.domainrdb.common.RandomGenerator.*;
import static com.concertticketing.domainrdb.common.ReflectionField.*;

import com.concertticketing.domainrdb.domain.company.domain.Company;

import lombok.Builder;

public final class CompanyFixture {
    private CompanyFixture() {
    }

    @Builder
    private static Company company(
        Integer id
    ) {
        Company company = new Company(
            randomText()
        );

        setField(company, "id", id != null ? id : randomPositiveNum());

        return company;
    }
}
