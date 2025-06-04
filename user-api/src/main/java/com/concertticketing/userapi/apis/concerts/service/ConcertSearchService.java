package com.concertticketing.userapi.apis.concerts.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.userapi.apis.concerts.constant.ConcertSort;
import com.concertticketing.userapi.apis.concerts.domain.Concert;
import com.concertticketing.userapi.apis.concerts.dto.ConcertListDto;
import com.concertticketing.userapi.apis.concerts.exception.ConcertErrorCode;
import com.concertticketing.userapi.apis.concerts.exception.ConcertErrorException;
import com.concertticketing.userapi.apis.concerts.repository.ConcertRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConcertSearchService {
    private final ConcertRepository concertRepository;

    @Transactional(readOnly = true)
    public Concert findConcert(Long id) {
        return concertRepository.findConcert(id)
            .orElseThrow(() -> new ConcertErrorException(ConcertErrorCode.CONCERT_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<ConcertListDto.ConcertListItem> findConcerts(ConcertSort sort, Pageable pageable) {
        Page<ConcertListDto.ConcertListItem> concerts = concertRepository.findConcerts(sort, pageable);
        if (concerts.getContent().isEmpty()) {
            throw new ConcertErrorException(ConcertErrorCode.PAGE_OVERFLOW);
        }
        return concerts;
    }
}
