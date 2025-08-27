package com.concertticketing.domainmongodb.domain.concertViewCount.repository;

import java.util.List;

import com.concertticketing.domainmongodb.domain.concertViewCount.domain.ConcertViewCount;

public interface ConcertViewCountRepositoryCustom {
    void bulkInsert(List<ConcertViewCount> viewCounts);
}
