package com.concertticketing.userapi.apis.concerts.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.commonerror.exception.common.CommonNotFoundException;
import com.concertticketing.domainrdb.domain.concert.domain.Concert;
import com.concertticketing.domainrdb.domain.concert.dto.ConcertListDto;
import com.concertticketing.domainrdb.domain.concert.dto.ConcertTicketingInfoDto;
import com.concertticketing.domainrdb.domain.concert.enums.ConcertSort;
import com.concertticketing.domainrdb.domain.concert.repository.ConcertRepository;

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

    public Page<ConcertListDto> findConcerts(ConcertSort sort, Pageable pageable) {
        return concertRepository.findConcerts(sort, pageable);
    }

    public ConcertTicketingInfoDto findConcertTicketingInfo(Long concertId) {
        return concertRepository.findConcertTicketingInfo(concertId)
            .orElseThrow(CommonNotFoundException::new);
    }

    public Long getConcertCount() {
        return concertRepository.count();
    }
}
