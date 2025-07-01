package com.concertticketing.sellerapi.apis.concerts.controller;

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

import com.concertticketing.sellerapi.apis.concerts.dto.AddConcertDto.AddConcertBody;
import com.concertticketing.sellerapi.apis.concerts.dto.AddConcertDto.AddConcertRes;
import com.concertticketing.sellerapi.apis.concerts.dto.ConcertDetailDto.ConcertDetailRes;
import com.concertticketing.sellerapi.apis.concerts.dto.ConcertListDto.ConcertListQuery;
import com.concertticketing.sellerapi.apis.concerts.dto.UpdateConcertDto.UpdateConcertBody;
import com.concertticketing.sellerapi.apis.concerts.usecase.ConcertUseCase;
import com.concertticketing.sellerapi.common.response.SuccessResponse;
import com.concertticketing.sellerapi.security.annotation.AuthenticatedManager;
import com.concertticketing.sellerapi.security.authentication.SecurityUserDetails;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Tag(name = "concert", description = "콘서트 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/concerts")
public class ConcertController {
    private final ConcertUseCase concertUseCase;

    @PostMapping
    @AuthenticatedManager
    public ResponseEntity<SuccessResponse<AddConcertRes>> addConcert(
        @AuthenticationPrincipal SecurityUserDetails userDetails,
        @RequestBody @Validated AddConcertBody body
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.from(
                concertUseCase.addConcert(userDetails.getCompanyId(), body)
            )
        );
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getConcerts(
        @AuthenticationPrincipal SecurityUserDetails userDetails,
        @ModelAttribute @Validated ConcertListQuery query
    ) {
        return ResponseEntity.ok(SuccessResponse.from(
                concertUseCase.getConcerts(userDetails.getCompanyId(), query)
            )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<ConcertDetailRes>> getConcert(
        @AuthenticationPrincipal SecurityUserDetails userDetails,
        @PathVariable @Min(1) Long id
    ) {
        return ResponseEntity.ok(SuccessResponse.from(
                concertUseCase.getConcert(id, userDetails.getCompanyId())
            )
        );
    }

    @PutMapping("/{id}")
    @AuthenticatedManager
    public ResponseEntity<SuccessResponse<?>> updateConcert(
        @AuthenticationPrincipal SecurityUserDetails userDetails,
        @PathVariable @Min(1) Long id,
        @RequestBody @Validated UpdateConcertBody body
    ) {
        concertUseCase.updateConcert(id, userDetails.getCompanyId(), body);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(SuccessResponse.noContent());
    }

    @DeleteMapping("/{id}")
    @AuthenticatedManager
    public ResponseEntity<SuccessResponse<?>> deleteConcert(
        @AuthenticationPrincipal SecurityUserDetails userDetails,
        @PathVariable @Min(1) Long id
    ) {
        concertUseCase.deleteConcert(id, userDetails.getCompanyId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(SuccessResponse.noContent());
    }
}
