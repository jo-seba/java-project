package com.concertticketing.kafkaconsumer.domain.concert.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.concertticketing.domainredis.domain.concert.repository.ConcertCacheRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertCacheSearchService {
    private final ConcertCacheRepository concertCacheRepository;

    public Optional<ConcertTokenUserCache> getConcertTokenUser(String token) {
        return concertCacheRepository.getConcertTokenUser(token);
    }
}
