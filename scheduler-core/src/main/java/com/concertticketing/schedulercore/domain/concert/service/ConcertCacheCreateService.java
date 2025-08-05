package com.concertticketing.schedulercore.domain.concert.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.concertticketing.domainredis.domain.concert.repository.ConcertCacheRepository;
import com.concertticketing.domainredis.domain.concert.repository.ConcertQueueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertCacheCreateService {
    private final ConcertCacheRepository concertCacheRepository;

    private final ConcertQueueRepository concertQueueRepository;

    public Long addConcertActiveTokens(Long concertId, long timestamp, List<String> tokens) {
        return concertQueueRepository.addConcertActiveTokens(concertId, timestamp, tokens);
    }

    public void setConcertLastWaitingCount(Long concertId, Long lastWaitingCount) {
        concertCacheRepository.setConcertLastWaitingCount(concertId, lastWaitingCount);
    }
}
