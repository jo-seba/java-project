package com.concertticketing.userapi.apis.concerts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concertticketing.commonerror.exception.common.CommonBadRequestException;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.concertticketing.userapi.apis.concerts.dto.ConcertTicketingEntryDto.ConcertTicketingEntryRes;
import com.concertticketing.userapi.apis.concerts.dto.ConcertTicketingStatusDto.ConcertTicketingStatusRes;
import com.concertticketing.userapi.apis.concerts.usecase.ConcertTicketingUseCase;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/concerts/{concertId}/tickets")
public class ConcertTicketingController {
    private final ConcertTicketingUseCase concertTicketingUseCase;

    @PostMapping("/entry")
    public ResponseEntity<ConcertTicketingEntryRes> attemptTicketingEntry(
        @AuthenticationPrincipal Long userId,
        @PathVariable @Min(1) Long concertId
    ) {
        return ResponseEntity.ok(
            concertTicketingUseCase.attemptTicketingEntry(userId, concertId)
        );
    }

    @GetMapping("/status")
    public ResponseEntity<ConcertTicketingStatusRes> getTicketingStatus(
        @AuthenticationPrincipal ConcertTokenUserCache concertTokenUserCache,
        @PathVariable @Min(1) Long concertId
    ) {
        if (!concertTokenUserCache.concertId().equals(concertId)) {
            throw new CommonBadRequestException();
        }
        return ResponseEntity.ok(
            concertTicketingUseCase.getTicketingStatus(concertTokenUserCache)
        );
    }
}
