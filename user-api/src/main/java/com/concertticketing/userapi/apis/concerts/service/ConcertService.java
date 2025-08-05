package com.concertticketing.userapi.apis.concerts.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.commonerror.exception.common.CommonNotFoundException;
import com.concertticketing.userapi.apis.concerts.constant.ConcertSort;
import com.concertticketing.userapi.apis.concerts.dbdto.ConcertListItemDBDto;
import com.concertticketing.userapi.apis.concerts.dbdto.ConcertTicketingInfoDBDto;
import com.concertticketing.userapi.apis.concerts.domain.Concert;
import com.concertticketing.userapi.apis.concerts.repository.ConcertRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertService {
    private final ConcertRepository concertRepository;

    // Read

    /**
     * @return concert with Venue, VenueLayout, ConcertCategories and ConcertCategory
     */
    public Concert findConcert(Long id) {
        return concertRepository.findConcert(id)
            .orElseThrow(CommonNotFoundException::new);
    }

    public Page<ConcertListItemDBDto> findConcerts(ConcertSort sort, Pageable pageable) {
        return concertRepository.findConcerts(sort, pageable);
    }

    public ConcertTicketingInfoDBDto findConcertTicketingInfo(Long concertId) {
        return concertRepository.findConcertTicketingInfo(concertId)
            .orElseThrow(CommonNotFoundException::new);
    }
}
