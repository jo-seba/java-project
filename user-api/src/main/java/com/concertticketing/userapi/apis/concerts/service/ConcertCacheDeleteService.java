package com.concertticketing.userapi.apis.concerts.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.concertticketing.domainredis.domain.concert.repository.ConcertCacheRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertCacheDeleteService {
    private final ConcertCacheRepository concertCacheRepository;

    public Optional<String> popConcertOpaqueToken() {
        return concertCacheRepository.leftPopConcertOpaqueToken();
    }
}
