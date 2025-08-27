package com.concertticketing.domainmongodb.domain.concertViewCount.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.concertticketing.domainmongodb.domain.concertViewCount.domain.ConcertViewCount;

public interface ConcertViewCountRepository
    extends MongoRepository<ConcertViewCount, ObjectId>, ConcertViewCountRepositoryCustom {
}
