package com.concertticketing.domainmongodb.domain.concertViewCount.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.concertticketing.domainmongodb.domain.concertViewCount.domain.ConcertViewCount;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConcertViewCountRepositoryCustomImpl implements ConcertViewCountRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public void bulkInsert(List<ConcertViewCount> viewCounts) {
        mongoTemplate.insertAll(viewCounts);
    }
}
