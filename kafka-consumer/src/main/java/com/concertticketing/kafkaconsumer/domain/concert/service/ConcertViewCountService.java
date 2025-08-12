package com.concertticketing.kafkaconsumer.domain.concert.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.concertticketing.domainmongodb.domain.concertViewCount.domain.ConcertViewCount;
import com.concertticketing.domainmongodb.domain.concertViewCount.repository.ConcertViewCountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertViewCountService {
    private final ConcertViewCountRepository concertViewCountRepository;

    public void bulkInsertViewCount(List<ConcertViewCount> viewCounts) {
        concertViewCountRepository.bulkInsert(viewCounts);
    }
}
