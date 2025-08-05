package com.concertticketing.userapi.apis.concerts.service;

import org.springframework.stereotype.Service;

import com.concertticketing.domainredis.domain.concert.repository.ConcertCacheRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertCacheUpdateService {
    private final ConcertCacheRepository concertCacheRepository;

    public Long incrConcertWaitingCount(Long concertId) {
        return concertCacheRepository.incrConcertWaitingCount(concertId);
    }
}
