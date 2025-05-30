package com.concertticketing.api.apis.concerts.service;

import com.concertticketing.api.apis.concerts.constant.ConcertSort;
import com.concertticketing.api.apis.concerts.domain.Concert;
import com.concertticketing.api.apis.concerts.dto.ConcertListDto;
import com.concertticketing.api.apis.concerts.exception.ConcertErrorCode;
import com.concertticketing.api.apis.concerts.exception.ConcertErrorException;
import com.concertticketing.api.apis.concerts.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertSearchService {
    private final ConcertRepository concertRepository;

    @Transactional(readOnly = true)
    public Concert findConcert(Long id){
        return concertRepository.findConcert(id).orElseThrow(() -> new ConcertErrorException(ConcertErrorCode.CONCERT_NOT_FOUND));
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
