package com.concertticketing.kafkaconsumer.domain.concert.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.domainrdb.domain.concert.dto.ConcertListDto;
import com.concertticketing.domainrdb.domain.concert.enums.ConcertSort;
import com.concertticketing.domainrdb.domain.concert.repository.ConcertJdbcRepository;
import com.concertticketing.domainrdb.domain.concert.repository.ConcertRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertService {
    private final ConcertRepository concertRepository;

    private final ConcertJdbcRepository concertJdbcRepository;

    public Page<ConcertListDto> findConcerts(ConcertSort sort, Pageable pageable) {
        return concertRepository.findConcerts(sort, pageable);
    }

    /**
     * Bulk update the view count for multiple concerts.
     *
     * @param concertIncrementMap key: concertId / value: viewCount to increment
     */
    @Transactional
    public void updateConcertsViewCount(Map<Long, Long> concertIncrementMap) {
        concertJdbcRepository.bulkUpdateViewCount(concertIncrementMap);
    }
}
