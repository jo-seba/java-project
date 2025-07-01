package com.concertticketing.sellerapi.apis.concerts.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.concerts.dbdto.ConcertDetailImageDBDto;
import com.concertticketing.sellerapi.apis.concerts.repository.ConcertDetailImageJdbcRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ConcertDetailImageCreateService {
    private final ConcertDetailImageJdbcRepository concertDetailImageJdbcRepository;

    public void bulkInsert(List<ConcertDetailImageDBDto> dtos) {
        concertDetailImageJdbcRepository.bulkInsert(dtos);
    }
}
