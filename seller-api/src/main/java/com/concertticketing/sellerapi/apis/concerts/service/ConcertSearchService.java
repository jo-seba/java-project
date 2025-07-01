package com.concertticketing.sellerapi.apis.concerts.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.concerts.domain.Concert;
import com.concertticketing.sellerapi.apis.concerts.repository.ConcertRepository;
import com.concertticketing.sellerapi.common.exception.CommonErrorCode;
import com.concertticketing.sellerapi.common.exception.GlobalErrorException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertSearchService {
    private final ConcertRepository concertRepository;

    public Concert findWithVenueAndVenueLayoutAndCategories(Long concertId, Integer companyId) {
        return concertRepository.findConcertWithVenueAndVenueLayoutAndCategories(concertId, companyId)
            .orElseThrow(() -> new GlobalErrorException(CommonErrorCode.NOT_FOUND));
    }

    public Page<Concert> findAllWithVenue(Integer companyId, Pageable pageable) {
        return concertRepository.findConcertsWithVenue(companyId, pageable);
    }
}
