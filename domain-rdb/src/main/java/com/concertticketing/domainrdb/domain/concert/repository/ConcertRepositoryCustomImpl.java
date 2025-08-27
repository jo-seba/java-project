package com.concertticketing.domainrdb.domain.concert.repository;

import static com.concertticketing.domainrdb.domain.concert.domain.QConcert.*;
import static com.concertticketing.domainrdb.domain.concert.domain.QConcertTicketingConfig.*;
import static com.concertticketing.domainrdb.domain.venue.domain.QVenue.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.concertticketing.domainrdb.domain.concert.domain.Concert;
import com.concertticketing.domainrdb.domain.concert.dto.ConcertListDto;
import com.concertticketing.domainrdb.domain.concert.dto.ConcertTicketingInfoDto;
import com.concertticketing.domainrdb.domain.concert.dto.QConcertListDto;
import com.concertticketing.domainrdb.domain.concert.dto.QConcertTicketingInfoDto;
import com.concertticketing.domainrdb.domain.concert.enums.ConcertSort;
import com.querydsl.core.types.OrderSpecifier;
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

    @Override
    public Page<ConcertListDto> findConcerts(ConcertSort sort, Pageable pageable) {
        List<ConcertListDto> content = queryFactory.select(
                new QConcertListDto(
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
    public Optional<ConcertTicketingInfoDto> findConcertTicketingInfo(Long concertId) {
        return Optional.ofNullable(
            queryFactory.select(
                    new QConcertTicketingInfoDto(
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
