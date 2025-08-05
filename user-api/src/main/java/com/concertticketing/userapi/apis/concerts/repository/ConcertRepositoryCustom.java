package com.concertticketing.userapi.apis.concerts.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.concertticketing.userapi.apis.concerts.constant.ConcertSort;
import com.concertticketing.userapi.apis.concerts.dbdto.ConcertListItemDBDto;
import com.concertticketing.userapi.apis.concerts.dbdto.ConcertTicketingInfoDBDto;

public interface ConcertRepositoryCustom {
    Page<ConcertListItemDBDto> findConcerts(ConcertSort sort, Pageable pageable);

    Optional<ConcertTicketingInfoDBDto> findConcertTicketingInfo(Long concertId);
}
