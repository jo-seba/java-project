package com.concertticketing.sellerapi.apis.sellers.mapper;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.concertticketing.domainrdb.domain.CompanyFixture;
import com.concertticketing.domainrdb.domain.SellerFixture;
import com.concertticketing.domainrdb.domain.company.domain.Company;
import com.concertticketing.domainrdb.domain.seller.domain.Seller;
import com.concertticketing.domainrdb.domain.seller.enums.SellerRole;
import com.concertticketing.sellerapi.apis.companies.dto.AddCompanySellerDto.AddCompanySellerBody;
import com.concertticketing.sellerapi.security.authentication.SecurityUserDetails;

@DisplayName("[Mapper] SellerMapper")
class SellerMapperTest {
    SellerMapper sellerMapper = Mappers.getMapper(SellerMapper.class);

    @DisplayName("Seller to SecurityUserDetails")
    @Test
    void toSecurityUserDetails() {
        Integer id = 1;
        SellerRole role = SellerRole.MANAGER;
        Integer companyId = 2;
        Seller seller = SellerFixture.sellerBuilder()
            .id(id)
            .role(role)
            .companyId(companyId)
            .build();

        SecurityUserDetails userDetails = sellerMapper.toSecurityUserDetails(seller);

        assertThat(userDetails.getId()).isEqualTo(id);
        assertThat(userDetails.getRole()).isEqualTo(role);
        assertThat(userDetails.getCompanyId()).isEqualTo(companyId);
        assertThat(userDetails.getPassword()).isNull();
    }

    @DisplayName("Company, AddCompanySellerBody to Seller")
    @Test
    void toSeller() {
        Integer companyId = 2;
        Company company = CompanyFixture.builder()
            .id(companyId)
            .build();
        SellerRole role = SellerRole.MANAGER;
        String email = "email";
        String name = "name";
        String phoneNumber = "01012345678";
        AddCompanySellerBody body = new AddCompanySellerBody(
            email,
            role,
            name,
            phoneNumber
        );

        Seller seller = sellerMapper.toSeller(company, body);

        assertThat(seller.getCompany().getId()).isEqualTo(companyId);
        assertThat(seller.getCompanyId()).isEqualTo(companyId);
        assertThat(seller.getEmail()).isEqualTo(email);
        assertThat(seller.getName()).isEqualTo(name);
        assertThat(seller.getRole()).isEqualTo(role);
        assertThat(seller.getPhoneNumber()).isEqualTo(phoneNumber);
    }
}