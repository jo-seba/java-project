package com.concertticketing.sellerapi.apis.concerts.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.commonerror.exception.common.CommonNotFoundException;
import com.concertticketing.sellerapi.apis.concerts.domain.Concert;
import com.concertticketing.sellerapi.apis.concerts.repository.ConcertRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertService {
    private final ConcertRepository concertRepository;

    // Create
    @Transactional
    public Concert saveConcert(Concert concert) {
        return concertRepository.save(concert);
    }

    // Read
    @Transactional(readOnly = true)
    public Concert findConcertWithVenueAndVenueLayoutAndCategories(Long concertId, Integer companyId) {
        return concertRepository.findConcertWithVenueAndVenueLayoutAndCategories(concertId, companyId)
            .orElseThrow(CommonNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<Concert> findConcertsWithVenue(Integer companyId, Pageable pageable) {
        return concertRepository.findConcertsWithVenue(companyId, pageable);
    }

    // Update

    // Delete
    @Transactional
    public void deleteConcert(Long id, Integer companyId) {
        concertRepository.deleteConcertByIdAndCompanyId(id, companyId);
    }
}
