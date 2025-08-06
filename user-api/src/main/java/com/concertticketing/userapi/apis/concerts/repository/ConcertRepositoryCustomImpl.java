package com.concertticketing.userapi.apis.concerts.repository;

import static com.concertticketing.userapi.apis.concerts.domain.QConcert.*;
import static com.concertticketing.userapi.apis.concerts.domain.QConcertTicketingConfig.*;
import static com.concertticketing.userapi.apis.venues.domain.QVenue.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.concertticketing.userapi.apis.concerts.constant.ConcertSort;
import com.concertticketing.userapi.apis.concerts.dbdto.ConcertListItemDBDto;
import com.concertticketing.userapi.apis.concerts.dbdto.ConcertTicketingInfoDBDto;
import com.concertticketing.userapi.apis.concerts.dbdto.QConcertListItemDBDto;
import com.concertticketing.userapi.apis.concerts.dbdto.QConcertTicketingInfoDBDto;
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

    @Override
    public Optional<ConcertTicketingInfoDBDto> findConcertTicketingInfo(Long concertId) {
        return Optional.ofNullable(
            queryFactory.select(
                    new QConcertTicketingInfoDBDto(
                        concert.id,
                        concert.bookingStartedAt,
                        concert.bookingEndedAt,
                        concertTicketingConfig.capacity
                    )
                ).from(concert)
                .leftJoin(concertTicketingConfig).on(concert.id.eq(concertTicketingConfig.id))
                .where(concert.id.eq(concertId))
                .fetchOne()
        );
    }

    private OrderSpecifier<?> getOrderByConcertSort(ConcertSort sort) {
        return switch (sort) {
            case NEWEST -> concert.createdAt.desc();
            case POPULAR -> concert.viewCount.desc();
        };
    }
}
