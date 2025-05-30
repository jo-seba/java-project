package com.concertticketing.api.apis.concerts.controller;

import com.concertticketing.api.apis.concerts.api.ConcertApi;
import com.concertticketing.api.apis.concerts.dto.ConcertDetailDto;
import com.concertticketing.api.apis.concerts.dto.ConcertDetailDto.ConcertDetailRes;
import com.concertticketing.api.apis.concerts.dto.ConcertListDto;
import com.concertticketing.api.apis.concerts.dto.ConcertListDto.ConcertListRes;
import com.concertticketing.api.apis.concerts.usecase.ConcertUseCase;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/concerts")
public class ConcertController implements ConcertApi {
    private final ConcertUseCase concertUseCase;

    @GetMapping
    public ResponseEntity<ConcertListRes> getConcerts(@ModelAttribute @Validated ConcertListDto.ConcertListQuery query) {
        return ResponseEntity.ok(concertUseCase.getConcerts(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConcertDetailRes> getConcert(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(concertUseCase.getConcert(id));
    }
}
