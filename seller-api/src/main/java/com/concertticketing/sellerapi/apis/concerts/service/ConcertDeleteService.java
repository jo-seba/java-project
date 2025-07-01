package com.concertticketing.sellerapi.apis.concerts.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.concerts.repository.ConcertRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ConcertDeleteService {
    private final ConcertRepository concertRepository;

    public void deleteConcert(Long id, Integer companyId) {
        concertRepository.deleteConcertByIdAndCompanyId(id, companyId);
    }
}
