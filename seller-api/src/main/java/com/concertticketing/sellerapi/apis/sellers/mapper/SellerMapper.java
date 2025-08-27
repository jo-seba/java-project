package com.concertticketing.sellerapi.apis.sellers.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.concertticketing.domainrdb.domain.company.domain.Company;
import com.concertticketing.domainrdb.domain.seller.domain.Seller;
import com.concertticketing.sellerapi.apis.companies.dto.AddCompanySellerDto;
import com.concertticketing.sellerapi.common.mapstruct.MapStructBaseConfig;
import com.concertticketing.sellerapi.security.authentication.SecurityUserDetails;

@Mapper(
    config = MapStructBaseConfig.class
)
public interface SellerMapper {
    SecurityUserDetails toSecurityUserDetails(Seller seller);

    @Mapping(target = "email", source = "body.email")
    @Mapping(target = "role", source = "body.role")
    @Mapping(target = "name", source = "body.name")
    @Mapping(target = "phoneNumber", source = "body.phoneNumber")
    Seller toSeller(Company company, AddCompanySellerDto.AddCompanySellerBody body);
}
