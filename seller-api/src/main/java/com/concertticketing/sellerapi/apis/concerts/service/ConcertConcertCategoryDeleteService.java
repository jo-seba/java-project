package com.concertticketing.sellerapi.apis.concerts.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.concerts.repository.ConcertConcertCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ConcertConcertCategoryDeleteService {
    private final ConcertConcertCategoryRepository concertConcertCategoryRepository;

    public int delete(List<Long> ids) {
        return concertConcertCategoryRepository.deleteConcertConcertCategories(ids);
    }
}
