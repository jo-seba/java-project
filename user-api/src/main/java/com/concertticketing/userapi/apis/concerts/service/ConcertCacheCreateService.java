package com.concertticketing.userapi.apis.concerts.service;

import org.springframework.stereotype.Service;

import com.concertticketing.domainredis.domain.concert.domain.ConcertTicketingCache;
import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.concertticketing.domainredis.domain.concert.repository.ConcertCacheRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertCacheCreateService {
    private final ConcertCacheRepository concertCacheRepository;

    public void setConcertTicketing(Long concertId, ConcertTicketingCache concertTicketing) {
        concertCacheRepository.setConcertTicketing(concertId, concertTicketing);
    }

    public Boolean setConcertTokenUserNX(String token, ConcertTokenUserCache concertTokenUser) {
        return concertCacheRepository.setConcertTokenUserNX(token, concertTokenUser);
    }
}
