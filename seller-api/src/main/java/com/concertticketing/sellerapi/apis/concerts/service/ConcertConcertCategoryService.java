package com.concertticketing.sellerapi.apis.concerts.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.concerts.dbdto.ConcertConcertCategoryDBDto;
import com.concertticketing.sellerapi.apis.concerts.repository.ConcertConcertCategoryJdbcRepository;
import com.concertticketing.sellerapi.apis.concerts.repository.ConcertConcertCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertConcertCategoryService {
    private final ConcertConcertCategoryRepository concertConcertCategoryRepository;

    private final ConcertConcertCategoryJdbcRepository concertConcertCategoryJdbcRepository;

    // Create
    @Transactional
    public void bulkInsertConcertConcertCategories(List<ConcertConcertCategoryDBDto> dtos) {
        concertConcertCategoryJdbcRepository.bulkInsert(dtos);
    }

    // Read

    // Update

    // Delete
    @Transactional
    public int deleteConcertConcertCategories(List<Long> ids) {
        return concertConcertCategoryRepository.deleteConcertConcertCategories(ids);
    }
}
