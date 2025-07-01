package com.concertticketing.sellerapi.apis.concerts.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.concerts.dbdto.ConcertConcertCategoryDBDto;
import com.concertticketing.sellerapi.apis.concerts.repository.ConcertConcertCategoryJdbcRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ConcertConcertCategoryCreateService {
    private final ConcertConcertCategoryJdbcRepository concertConcertCategoryJdbcRepository;

    public void bulkInsert(List<ConcertConcertCategoryDBDto> dtos) {
        concertConcertCategoryJdbcRepository.bulkInsert(dtos);
    }
}
