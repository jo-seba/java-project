package com.concertticketing.userapi.apis.concerts.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.concertticketing.domainredis.domain.concert.domain.ConcertTicketingCache;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.concertticketing.domainredis.domain.concert.repository.ConcertCacheRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertCacheSearchService {
    private final ConcertCacheRepository concertCacheRepository;

    public Optional<ConcertTicketingCache> getConcertTicketing(Long id) {
        return concertCacheRepository.getConcertTicketing(id);
    }

    public Optional<ConcertTokenUserCache> getConcertTokenUser(String token) {
        return concertCacheRepository.getConcertTokenUser(token);
    }

    public Long getConcertLastWaitingCount(Long concertId) {
        return concertCacheRepository.getConcertLastWaitingCount(concertId);
    }
}
