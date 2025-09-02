package com.concertticketing.sellerapi.apis.companies.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.concertticketing.commonerror.exception.common.CommonBadRequestException;
import com.concertticketing.domainrdb.domain.seller.dto.SellerListDto;
import com.concertticketing.domainrdb.domain.seller.enums.SellerRole;
import com.concertticketing.sellerapi.apis.companies.dto.AddCompanySellerDto;
import com.concertticketing.sellerapi.apis.companies.dto.CompanySellersDto;
import com.concertticketing.sellerapi.apis.companies.dto.UpdateCompanySellerDto;
import com.concertticketing.sellerapi.apis.companies.service.CompanyService;
import com.concertticketing.sellerapi.apis.sellers.mapper.SellerMapper;
import com.concertticketing.sellerapi.apis.sellers.service.SellerService;
import com.concertticketing.sellerapi.common.annotation.Facade;
import com.concertticketing.sellerapi.security.authentication.SecurityUserDetails;

import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class CompanySellerFacade {
    private final SellerMapper sellerMapper;

    private final CompanyService companyService;

    private final SellerService sellerService;

    public final int DEFAULT_PAGE_SIZE = 10;

    public void addCompanySeller(Integer companyId, AddCompanySellerDto.AddCompanySellerBody body) {
        sellerService.saveSeller(sellerMapper.toSeller(companyService.getCompanyReference(companyId), body));
    }

    public CompanySellersDto.CompanySellerListRes getCompanySellers(
        Integer companyId,
        CompanySellersDto.CompanySellerListQuery query
    ) {
        Page<SellerListDto> sellers = sellerService.findSellers(
            companyId,
            query.sort(),
            PageRequest.of(query.getPageablePage(), DEFAULT_PAGE_SIZE)
        );

        return new CompanySellersDto.CompanySellerListRes(
            query.page(),
            sellers.getTotalPages(),
            sellers.getContent()
        );
    }

    public void updateCompanySeller(
        SecurityUserDetails userDetails,
        Integer sellerId,
        UpdateCompanySellerDto.UpdateCompanySellerBody body
    ) {
        boolean isSelf = userDetails.getId().equals(sellerId);
        boolean isOwnerRoleBody = body.role() == SellerRole.OWNER;
        // 수정할 값이 자신일 때는 role == owner 그 외에는 role != owner
        if (isSelf != isOwnerRoleBody) {
            throw new CommonBadRequestException();
        }

        sellerService.updateSeller(
            sellerId, userDetails.getCompanyId(),
            body.role(), body.name(), body.phoneNumber()
        );
    }

    public void deleteCompanySeller(
        SecurityUserDetails userDetails,
        Integer sellerId
    ) {
        if (userDetails.getId().equals(sellerId)) {
            throw new CommonBadRequestException();
        }
        sellerService.deleteSeller(sellerId, userDetails.getCompanyId());
    }
}
