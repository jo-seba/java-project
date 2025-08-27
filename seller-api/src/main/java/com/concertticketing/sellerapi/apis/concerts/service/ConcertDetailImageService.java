package com.concertticketing.sellerapi.apis.concerts.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.domainrdb.domain.concert.dto.ConcertDetailImageDto;
import com.concertticketing.domainrdb.domain.concert.repository.ConcertDetailImageJdbcRepository;
import com.concertticketing.domainrdb.domain.concert.repository.ConcertDetailImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertDetailImageService {
    private final ConcertDetailImageRepository concertDetailImageRepository;

    private final ConcertDetailImageJdbcRepository concertDetailImageJdbcRepository;

    // Create
    @Transactional
    public void bulkInsertConcertDetailImages(List<ConcertDetailImageDto> dtos) {
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
