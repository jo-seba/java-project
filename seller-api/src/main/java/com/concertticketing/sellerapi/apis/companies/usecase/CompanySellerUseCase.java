package com.concertticketing.sellerapi.apis.companies.usecase;

import static com.concertticketing.sellerapi.common.constant.PageConstants.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.concertticketing.commonerror.exception.common.CommonBadRequestException;
import com.concertticketing.sellerapi.apis.companies.dto.AddCompanySellerDto;
import com.concertticketing.sellerapi.apis.companies.dto.CompanySellerListDto;
import com.concertticketing.sellerapi.apis.companies.dto.CompanySellerListDto.CompanySellerListQuery;
import com.concertticketing.sellerapi.apis.companies.dto.CompanySellerListDto.CompanySellerListRes;
import com.concertticketing.sellerapi.apis.companies.dto.UpdateCompanySellerDto.UpdateCompanySellerBody;
import com.concertticketing.sellerapi.apis.sellers.constant.SellerRole;
import com.concertticketing.sellerapi.apis.sellers.mapper.SellerMapper;
import com.concertticketing.sellerapi.apis.sellers.service.SellerCreateService;
import com.concertticketing.sellerapi.apis.sellers.service.SellerDeleteService;
import com.concertticketing.sellerapi.apis.sellers.service.SellerSearchService;
import com.concertticketing.sellerapi.apis.sellers.service.SellerUpdateService;
import com.concertticketing.sellerapi.common.annotation.UseCase;
import com.concertticketing.sellerapi.security.authentication.SecurityUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CompanySellerUseCase {
    private final SellerMapper sellerMapper;

    private final SellerCreateService sellerCreateService;
    private final SellerSearchService sellerSearchService;
    private final SellerUpdateService sellerUpdateService;
    private final SellerDeleteService sellerDeleteService;

    public void addCompanySeller(Integer companyId, AddCompanySellerDto.AddCompanySellerBody body) {
        sellerCreateService.save(sellerMapper.toSeller(companyId, body));
    }

    public CompanySellerListRes getCompanySellers(
        Integer companyId,
        CompanySellerListQuery query
    ) {
        Page<CompanySellerListDto.CompanySellerListItem> sellers = sellerSearchService.findSellers(
            companyId,
            query.sort(),
            PageRequest.of(query.getPageablePage(), DEFAULT_PAGE_SIZE)
        );

        return new CompanySellerListRes(
            query.page(),
            sellers.getTotalPages(),
            sellers.getContent()
        );
    }

    public void updateCompanySeller(
        SecurityUserDetails userDetails,
        Integer sellerId,
        UpdateCompanySellerBody body
    ) {
        boolean isSelf = userDetails.getId().equals(sellerId);
        boolean isOwnerRoleBody = body.role() == SellerRole.OWNER;
        // 수정할 값이 자신일 때는 role == owner 그 외에는 role != owner
        if (isSelf != isOwnerRoleBody) {
            throw new CommonBadRequestException();
        }

        sellerUpdateService.update(
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
        sellerDeleteService.delete(sellerId, userDetails.getCompanyId());
    }
}
