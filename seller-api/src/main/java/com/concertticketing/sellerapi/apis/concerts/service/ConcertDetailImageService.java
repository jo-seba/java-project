package com.concertticketing.sellerapi.apis.concerts.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.concerts.dbdto.ConcertDetailImageDBDto;
import com.concertticketing.sellerapi.apis.concerts.repository.ConcertDetailImageJdbcRepository;
import com.concertticketing.sellerapi.apis.concerts.repository.ConcertDetailImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertDetailImageService {
    private final ConcertDetailImageRepository concertDetailImageRepository;

    private final ConcertDetailImageJdbcRepository concertDetailImageJdbcRepository;

    // Create
    @Transactional
    public void bulkInsertConcertDetailImages(List<ConcertDetailImageDBDto> dtos) {
        concertDetailImageJdbcRepository.bulkInsert(dtos);
    }

    // Read

    // Update

    // Delete
    @Transactional
    public int deleteConcertDetailImages(List<Long> ids) {
        return concertDetailImageRepository.deleteConcertDetailImages(ids);
    }
}
