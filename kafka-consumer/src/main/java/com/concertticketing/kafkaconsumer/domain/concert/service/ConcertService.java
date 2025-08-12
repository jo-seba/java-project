package com.concertticketing.kafkaconsumer.domain.concert.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.kafkaconsumer.common.constant.ConcertSort;
import com.concertticketing.kafkaconsumer.domain.concert.dto.ConcertListItemDBDto;
import com.concertticketing.kafkaconsumer.domain.concert.repository.ConcertRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertService {
    private final ConcertRepository concertRepository;

    public Page<ConcertListItemDBDto> findConcerts(ConcertSort sort, Pageable pageable) {
        return concertRepository.findConcerts(sort, pageable);
    }
}
