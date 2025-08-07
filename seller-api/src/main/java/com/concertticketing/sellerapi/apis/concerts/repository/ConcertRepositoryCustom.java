package com.concertticketing.sellerapi.apis.concerts.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.concertticketing.sellerapi.apis.concerts.domain.Concert;

public interface ConcertRepositoryCustom {
    Page<Concert> findConcertsWithVenue(Integer companyId, Pageable pageable);
}
