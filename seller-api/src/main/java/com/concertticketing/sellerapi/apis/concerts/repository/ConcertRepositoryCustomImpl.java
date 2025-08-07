package com.concertticketing.sellerapi.apis.concerts.repository;

import static com.concertticketing.sellerapi.apis.concerts.domain.QConcert.*;
import static com.concertticketing.sellerapi.apis.venues.domain.QVenue.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.concertticketing.sellerapi.apis.concerts.domain.Concert;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConcertRepositoryCustomImpl implements ConcertRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<Concert> findConcertsWithVenue(Integer companyId, Pageable pageable) {
        List<Concert> content = queryFactory.selectFrom(concert)
            .join(concert.venue, venue).fetchJoin()
            .where(concert.company.id.eq(companyId))
            .orderBy(concert.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(concert.count()).from(concert);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
