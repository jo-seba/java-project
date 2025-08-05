package com.concertticketing.kafkaconsumer.domain.concert.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.concertticketing.domainredis.domain.concert.repository.ConcertQueueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertCacheDeleteService {
    private final ConcertQueueRepository concertQueueRepository;

    public Long removeConcertActiveTokens(List<String> tokens) {
        return concertQueueRepository.removeConcertActiveTokens(tokens.toArray());
    }

    public List<String> removeUserConcertOldTokens(Long concertId, Long userId) {
        return concertQueueRepository.retainFirstPopRestConcertUserToken(concertId, userId);
    }

    public Long removeConcertWaitingTokens(Long concertId, List<String> tokens) {
        return concertQueueRepository.removeConcertWaitingTokens(concertId, tokens.toArray());
    }

    public Long removeConcertActiveTokens(Long concertId, List<String> tokens) {
        return concertQueueRepository.removeConcertActiveTokens(concertId, tokens.toArray());
    }

    public Long removeConcertWaitingTokenHeartbeats(Long concertId, List<String> tokens) {
        return concertQueueRepository.removeConcertWaitingTokenHeartbeats(concertId, tokens.toArray());
    }
}
