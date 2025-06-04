package com.concertticketing.userapi.apis.concerts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concertticketing.userapi.apis.concerts.api.ConcertApi;
import com.concertticketing.userapi.apis.concerts.dto.ConcertDetailDto.ConcertDetailRes;
import com.concertticketing.userapi.apis.concerts.dto.ConcertListDto;
import com.concertticketing.userapi.apis.concerts.dto.ConcertListDto.ConcertListRes;
import com.concertticketing.userapi.apis.concerts.usecase.ConcertUseCase;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/concerts")
public class ConcertController implements ConcertApi {
    private final ConcertUseCase concertUseCase;

    @GetMapping
    public ResponseEntity<ConcertListRes> getConcerts(
        @ModelAttribute @Validated ConcertListDto.ConcertListQuery query) {
        return ResponseEntity.ok(concertUseCase.getConcerts(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConcertDetailRes> getConcert(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(concertUseCase.getConcert(id));
    }
}
