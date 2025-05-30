package com.concertticketing.api.apis.concerts.repository;

import com.concertticketing.api.apis.concerts.constant.ConcertSort;
import com.concertticketing.api.apis.concerts.dto.ConcertListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConcertRepositoryCustom {
    Page<ConcertListDto.ConcertListItem> findConcerts(ConcertSort sort, Pageable pageable);
}
