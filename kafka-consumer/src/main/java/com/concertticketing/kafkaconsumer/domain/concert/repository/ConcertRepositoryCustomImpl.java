package com.concertticketing.kafkaconsumer.domain.concert.repository;

import static com.concertticketing.kafkaconsumer.domain.concert.domain.QConcert.*;
import static com.concertticketing.kafkaconsumer.domain.venue.domain.QVenue.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.concertticketing.kafkaconsumer.common.constant.ConcertSort;
import com.concertticketing.kafkaconsumer.domain.concert.dto.ConcertListItemDBDto;
import com.concertticketing.kafkaconsumer.domain.concert.dto.QConcertListItemDBDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConcertRepositoryCustomImpl implements ConcertRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ConcertListItemDBDto> findConcerts(ConcertSort sort, Pageable pageable) {
        List<ConcertListItemDBDto> content = queryFactory.select(
                new QConcertListItemDBDto(
                    concert.id,
                    concert.title,
                    venue.name,
                    concert.thumbnail,
                    concert.startedAt,
                    concert.endedAt
                )
            ).from(concert)
            .join(concert.venue, venue)
            .orderBy(getOrderByConcertSort(sort))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(concert.count()).from(concert);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> getOrderByConcertSort(ConcertSort sort) {
        return switch (sort) {
            case NEWEST -> concert.createdAt.desc();
            case POPULAR -> concert.viewCount.desc();
        };
    }
}
