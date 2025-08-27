package com.concertticketing.kafkaconsumer.domain.concert.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.concertticketing.kafkaconsumer.common.constant.ConcertSort;
import com.concertticketing.kafkaconsumer.domain.concert.dto.ConcertListItemDBDto;

public interface ConcertRepositoryCustom {
    Page<ConcertListItemDBDto> findConcerts(ConcertSort sort, Pageable pageable);
}
