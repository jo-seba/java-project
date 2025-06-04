package com.concertticketing.userapi.apis.concerts.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.concertticketing.userapi.apis.concerts.constant.ConcertSort;
import com.concertticketing.userapi.apis.concerts.dto.ConcertListDto;

public interface ConcertRepositoryCustom {
    Page<ConcertListDto.ConcertListItem> findConcerts(ConcertSort sort, Pageable pageable);
}
