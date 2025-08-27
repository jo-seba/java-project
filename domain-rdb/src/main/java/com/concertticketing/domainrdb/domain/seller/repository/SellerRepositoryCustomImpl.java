package com.concertticketing.domainrdb.domain.seller.repository;

import static com.concertticketing.domainrdb.domain.seller.domain.QSeller.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.concertticketing.domainrdb.domain.seller.dto.QSellerListDto;
import com.concertticketing.domainrdb.domain.seller.dto.SellerListDto;
import com.concertticketing.domainrdb.domain.seller.enums.SellerSort;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SellerRepositoryCustomImpl implements SellerRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SellerListDto> findSellersDto(
        Integer companyId,
        SellerSort sort,
        Pageable pageable
    ) {
        JPAQuery<SellerListDto> query = queryFactory.select(
                new QSellerListDto(
                    seller.id,
                    seller.email,
                    seller.role,
                    seller.name,
                    seller.phoneNumber
                )
            ).from(seller)
            .where(seller.company.id.eq(companyId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());

        if (sort != null) {
            query.orderBy(toOrderSpecifier(sort));
        }

        List<SellerListDto> result = query.fetch();

        JPAQuery<Long> countQuery = queryFactory.select(seller.count()).from(seller);

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> toOrderSpecifier(SellerSort sort) {
        return switch (sort) {
            case NEWEST -> seller.createdAt.desc();
            case OLDEST -> seller.createdAt.asc();
        };
    }
}
