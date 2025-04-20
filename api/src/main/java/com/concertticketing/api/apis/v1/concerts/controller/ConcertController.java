package com.concertticketing.api.apis.v1.concerts.controller;

import com.concertticketing.api.apis.v1.concerts.dto.ConcertDetailResDto;
import com.concertticketing.api.apis.v1.concerts.dto.ConcertListQueryReqDto;
import com.concertticketing.api.apis.v1.concerts.dto.ConcertListResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Console;

@RestController
@RequestMapping("/api/v1/concerts")
public class ConcertController {
    @GetMapping
    public ResponseEntity<ConcertListResDto> getConcerts(@ModelAttribute ConcertListQueryReqDto reqDto) {
        return ResponseEntity.ok(ConcertListResDto.fromEntity());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConcertDetailResDto> getConcert(@PathVariable int id) {
        return ResponseEntity.ok(ConcertDetailResDto.fromEntity());
    }
}
