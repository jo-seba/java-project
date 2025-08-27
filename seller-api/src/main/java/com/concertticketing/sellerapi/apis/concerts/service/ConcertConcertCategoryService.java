package com.concertticketing.sellerapi.apis.concerts.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.domainrdb.domain.concert.dto.ConcertConcertCategoryDto;
import com.concertticketing.domainrdb.domain.concert.repository.ConcertConcertCategoryJdbcRepository;
import com.concertticketing.domainrdb.domain.concert.repository.ConcertConcertCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertConcertCategoryService {
    private final ConcertConcertCategoryRepository concertConcertCategoryRepository;

    private final ConcertConcertCategoryJdbcRepository concertConcertCategoryJdbcRepository;

    // Create
    @Transactional
    public void bulkInsertConcertConcertCategories(List<ConcertConcertCategoryDto> dtos) {
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
