package com.concertticketing.schedulercore.domain.concert.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.concertticketing.domainredis.domain.concert.repository.ConcertCacheRepository;
import com.concertticketing.domainredis.domain.concert.repository.ConcertQueueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertCacheDeleteService {
    private final ConcertCacheRepository concertCacheRepository;
    private final ConcertQueueRepository concertQueueRepository;

    public Long unlinkConcertTicketUsers(Set<String> tokens) {
        if (CollectionUtils.isEmpty(tokens)) {
            return 0L;
        }
        return concertCacheRepository.unlinkConcertTokenUsers(tokens);
    }

    public Long removeConcertActiveTokens(Set<String> tokens) {
        if (CollectionUtils.isEmpty(tokens)) {
            return 0L;
        }
        return concertQueueRepository.removeConcertActiveTokens(tokens.toArray());
    }

    public Long removeConcertWaitingTokens(Long concertId, List<String> tokens) {
        return concertQueueRepository.removeConcertWaitingTokens(concertId, tokens.toArray());
    }

    public Long removeConcertWaitingTokens(Long concertId, Set<String> tokens) {
        return concertQueueRepository.removeConcertWaitingTokens(concertId, tokens.toArray());
    }

    public Long removeConcertActiveTokens(Long concertId, Set<String> tokens) {
        if (CollectionUtils.isEmpty(tokens)) {
            return 0L;
        }
        return concertQueueRepository.removeConcertActiveTokens(concertId, tokens.toArray());
    }

    public Long removeConcertWaitingTokenHeartbeats(Long concertId, Set<String> tokens) {
        if (CollectionUtils.isEmpty(tokens)) {
            return 0L;
        }
        return concertQueueRepository.removeConcertWaitingTokenHeartbeats(concertId, tokens.toArray());
    }
}
