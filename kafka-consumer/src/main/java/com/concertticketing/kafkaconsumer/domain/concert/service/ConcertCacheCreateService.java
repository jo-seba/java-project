package com.concertticketing.kafkaconsumer.domain.concert.service;

import org.springframework.stereotype.Service;

import com.concertticketing.domainredis.domain.concert.domain.ConcertTokenUserCache;
import com.concertticketing.domainredis.domain.concert.repository.ConcertCacheRepository;
import com.concertticketing.domainredis.domain.concert.repository.ConcertQueueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertCacheCreateService {
    private final ConcertCacheRepository concertCacheRepository;

    private final ConcertQueueRepository concertQueueRepository;

    public void setConcertTokenUser(String token, ConcertTokenUserCache concertTokenUser) {
        concertCacheRepository.setConcertTokenUser(token, concertTokenUser);
    }

    public Boolean addConcertActiveTokenNX(String token, long createdAt) {
        return concertQueueRepository.addConcertActiveTokenNX(token, createdAt);
    }

    public Long leftPushConcertUserToken(Long concertId, Long userId, String token) {
        return concertQueueRepository.leftPushConcertUserToken(concertId, userId, token);
    }

    public Boolean addConcertWaitingTokenNX(Long concertId, String token, long createdAt) {
        return concertQueueRepository.addConcertWaitingTokenNX(concertId, token, createdAt);
    }

    public void addConcertWaitingTokenHeartbeat(Long concertId, String token, long timestamp) {
        concertQueueRepository.addConcertWaitingTokenHeartbeat(concertId, token, timestamp);
    }
}
