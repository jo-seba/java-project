package com.concertticketing.domainrdb.domain.concert.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.concertticketing.domainrdb.domain.concert.domain.Concert;
import com.concertticketing.domainrdb.domain.concert.dto.ConcertListDto;
import com.concertticketing.domainrdb.domain.concert.dto.ConcertTicketingInfoDto;
import com.concertticketing.domainrdb.domain.concert.enums.ConcertSort;

public interface ConcertRepositoryCustom {
    Page<Concert> findConcertsWithVenue(Integer companyId, Pageable pageable);

    Page<ConcertListDto> findConcerts(ConcertSort sort, Pageable pageable);

    Optional<ConcertTicketingInfoDto> findConcertTicketingInfo(Long concertId);
}
