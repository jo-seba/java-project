package com.concertticketing.sellerapi.apis.companies.controller;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concertticketing.sellerapi.apis.companies.dto.AddCompanySellerDto.AddCompanySellerBody;
import com.concertticketing.sellerapi.apis.companies.dto.CompanySellersDto.CompanySellerListQuery;
import com.concertticketing.sellerapi.apis.companies.dto.CompanySellersDto.CompanySellerListRes;
import com.concertticketing.sellerapi.apis.companies.dto.UpdateCompanySellerDto.UpdateCompanySellerBody;
import com.concertticketing.sellerapi.apis.companies.facade.CompanySellerFacade;
import com.concertticketing.sellerapi.common.response.SuccessResponse;
import com.concertticketing.sellerapi.security.annotation.AuthenticatedManager;
import com.concertticketing.sellerapi.security.annotation.AuthenticatedOwner;
import com.concertticketing.sellerapi.security.authentication.SecurityUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "company seller", description = "회사 내 직원 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies/sellers")
public class CompanySellerController {
    private final CompanySellerFacade companySellerFacade;

    @Operation(
        summary = "회사 내 직원 추가",
        description = "OWNER 권한만 호출할 수 있습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "값 반환 없음"),
        @ApiResponse(responseCode = "400", description = "bad request"),
        @ApiResponse(responseCode = "403", description = "OWNER만 가능"),
        @ApiResponse(responseCode = "409", description = "email 중복")
    })
    @PostMapping
    @AuthenticatedOwner
    public ResponseEntity<SuccessResponse<?>> addCompanySeller(
        @AuthenticationPrincipal SecurityUserDetails userDetails,
        @RequestBody @Validated AddCompanySellerBody body
    ) {
        companySellerFacade.addCompanySeller(userDetails.getCompanyId(), body);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created());
    }

    @Operation(
        summary = "회사 내 직원 조회",
        description = "MANAGER or OWNER 권한만 호출할 수 있습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "값 반환 없음"),
        @ApiResponse(responseCode = "400", description = "bad request"),
        @ApiResponse(responseCode = "403", description = "MANAGER or OWNER 가능")
    })
    @GetMapping
    @AuthenticatedManager
    public ResponseEntity<SuccessResponse<CompanySellerListRes>> getCompanySellers(
        @AuthenticationPrincipal SecurityUserDetails userDetails,
        @ModelAttribute @Validated CompanySellerListQuery query
    ) {
        return ResponseEntity.ok(SuccessResponse.from(
            companySellerFacade.getCompanySellers(
                userDetails.getCompanyId(),
                query
            )
        ));
    }

    @Operation(
        summary = "회사 내 직원 수정",
        description = "본인 수정 시 역할 owner만 가능 그 외 owner 제외 가능"
    )
    @Parameter(name = "sellerId", required = true, in = PATH)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "값 반환 없음"),
        @ApiResponse(responseCode = "400", description = "bad request + description 조건 지키지 않았을 떄"),
        @ApiResponse(responseCode = "403", description = "OWNER만 가능")
    })
    @PutMapping("/{sellerId}")
    @AuthenticatedOwner
    public ResponseEntity<SuccessResponse<?>> updateCompanySeller(
        @AuthenticationPrincipal SecurityUserDetails userDetails,
        @PathVariable Integer sellerId,
        @RequestBody @Validated UpdateCompanySellerBody body
    ) {
        companySellerFacade.updateCompanySeller(userDetails, sellerId, body);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(SuccessResponse.noContent());
    }

    @Operation(
        summary = "회사 내 직원 수정",
        description = "본인 수정 시 역할 owner만 가능 그 외 owner 제외 가능"
    )
    @Parameter(name = "sellerId", required = true, in = PATH)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "값 반환 없음"),
        @ApiResponse(responseCode = "400", description = "bad request + description 조건 지키지 않았을 떄"),
        @ApiResponse(responseCode = "403", description = "OWNER만 가능")
    })
    @DeleteMapping("/{sellerId}")
    @AuthenticatedOwner
    public ResponseEntity<SuccessResponse<?>> deleteCompanySeller(
        @AuthenticationPrincipal SecurityUserDetails userDetails,
        @PathVariable Integer sellerId
    ) {
        companySellerFacade.deleteCompanySeller(userDetails, sellerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(SuccessResponse.noContent());
    }
}
