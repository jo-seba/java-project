package com.concertticketing.sellerapi.apis.concerts.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.concerts.domain.Concert;
import com.concertticketing.sellerapi.apis.concerts.repository.ConcertRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ConcertCreateService {
    private final ConcertRepository concertRepository;

    public Concert save(Concert concert) {
        return concertRepository.save(concert);
    }
}
