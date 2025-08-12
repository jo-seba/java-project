package com.concertticketing.userapi.apis.concerts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.concertticketing.userapi.apis.concerts.dto.HoldConcertSeatDto.HoldConcertSeatReq;
import com.concertticketing.userapi.apis.concerts.dto.HoldConcertSeatDto.HoldConcertSeatRes;
import com.concertticketing.userapi.apis.concerts.dto.PerformConcertSeatPaymentDto.PerformConcertSeatPaymentReq;
import com.concertticketing.userapi.apis.concerts.dto.PerformConcertSeatPaymentDto.PerformConcertSeatPaymentRes;
import com.concertticketing.userapi.apis.concerts.facade.ConcertSeatFacade;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/concerts/{concertId}/seats")
public class ConcertSeatController {
    private final ConcertSeatFacade concertSeatFacade;

    @PostMapping("/hold")
    public ResponseEntity<HoldConcertSeatRes> holdConcertSeat(
        @AuthenticationPrincipal ConcertTokenUserCache concertTokenUserCache,
        @PathVariable @Min(1) Long concertId,
        @RequestBody @Validated HoldConcertSeatReq body
    ) {
        return ResponseEntity.ok(
            concertSeatFacade.holdConcertSeat(
                concertTokenUserCache.userId(),
                concertId,
                body
            )
        );
    }

    @PostMapping("/payment")
    public ResponseEntity<PerformConcertSeatPaymentRes> performConcertSeatPayment(
        @AuthenticationPrincipal ConcertTokenUserCache concertTokenUserCache,
        @PathVariable @Min(1) Long concertId,
        @RequestBody @Validated PerformConcertSeatPaymentReq body
    ) {
        return ResponseEntity.ok(
            concertSeatFacade.performConcertSeatPayment(
                concertTokenUserCache.userId(),
                concertId,
                body
            )
        );
    }
}
