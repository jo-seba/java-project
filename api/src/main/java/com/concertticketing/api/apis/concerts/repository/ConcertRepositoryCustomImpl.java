package com.concertticketing.api.apis.concerts.repository;

import com.concertticketing.api.apis.concerts.constant.ConcertSort;
import com.concertticketing.api.apis.concerts.domain.QConcert;
import com.concertticketing.api.apis.concerts.dto.ConcertListDto;
import com.concertticketing.api.apis.concerts.dto.QConcertListDto_ConcertListItem;
import com.concertticketing.api.apis.venues.domain.QVenue;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

@RequiredArgsConstructor
public class ConcertRepositoryCustomImpl implements ConcertRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private final QConcert concert = QConcert.concert;
    private final QVenue venue = QVenue.venue;

    @Override
    public Page<ConcertListDto.ConcertListItem> findConcerts(ConcertSort sort, Pageable pageable) {
        List<ConcertListDto.ConcertListItem> content = queryFactory.select(
                    new QConcertListDto_ConcertListItem(
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
