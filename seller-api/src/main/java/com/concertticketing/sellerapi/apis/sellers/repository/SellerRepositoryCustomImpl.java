package com.concertticketing.sellerapi.apis.sellers.repository;

import static com.concertticketing.sellerapi.apis.sellers.domain.QSeller.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.concertticketing.sellerapi.apis.companies.constant.CompanySellerSort;
import com.concertticketing.sellerapi.apis.companies.dto.CompanySellerListDto;
import com.concertticketing.sellerapi.apis.companies.dto.QCompanySellerListDto_CompanySellerListItem;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SellerRepositoryCustomImpl implements SellerRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CompanySellerListDto.CompanySellerListItem> findSellersDto(
        Integer companyId,
        CompanySellerSort sort,
        Pageable pageable
    ) {
        JPAQuery<CompanySellerListDto.CompanySellerListItem> query = queryFactory.select(
                new QCompanySellerListDto_CompanySellerListItem(
                    seller.id,
                    seller.email,
                    seller.role,
                    seller.name,
                    seller.phoneNumber
                )
            ).from(seller)
            .where(seller.companyId.eq(companyId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());

        if (sort != null) {
            query.orderBy(toOrderSpecifier(sort));
        }

        List<CompanySellerListDto.CompanySellerListItem> result = query.fetch();

        JPAQuery<Long> countQuery = queryFactory.select(seller.count()).from(seller);

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> toOrderSpecifier(CompanySellerSort sort) {
        return switch (sort) {
            case NEWEST -> seller.createdAt.desc();
            case OLDEST -> seller.createdAt.asc();
        };
    }
}
