package com.concertticketing.sellerapi.apis.companies.facade;

import static com.concertticketing.sellerapi.common.constant.PageConstants.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.concertticketing.commonerror.exception.common.CommonBadRequestException;
import com.concertticketing.sellerapi.apis.companies.dto.AddCompanySellerDto;
import com.concertticketing.sellerapi.apis.companies.dto.CompanySellerListDto;
import com.concertticketing.sellerapi.apis.companies.dto.UpdateCompanySellerDto;
import com.concertticketing.sellerapi.apis.sellers.constant.SellerRole;
import com.concertticketing.sellerapi.apis.sellers.mapper.SellerMapper;
import com.concertticketing.sellerapi.apis.sellers.service.SellerService;
import com.concertticketing.sellerapi.common.annotation.Facade;
import com.concertticketing.sellerapi.security.authentication.SecurityUserDetails;

import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class CompanySellerFacade {
    private final SellerMapper sellerMapper;

    private final SellerService sellerService;

    public void addCompanySeller(Integer companyId, AddCompanySellerDto.AddCompanySellerBody body) {
        sellerService.saveSeller(sellerMapper.toSeller(companyId, body));
    }

    public CompanySellerListDto.CompanySellerListRes getCompanySellers(
        Integer companyId,
        CompanySellerListDto.CompanySellerListQuery query
    ) {
        Page<CompanySellerListDto.CompanySellerListItem> sellers = sellerService.findSellers(
            companyId,
            query.sort(),
            PageRequest.of(query.getPageablePage(), DEFAULT_PAGE_SIZE)
        );

        return new CompanySellerListDto.CompanySellerListRes(
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
