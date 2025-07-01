package com.concertticketing.sellerapi.apis.concerts.usecase;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.concertticketing.sellerapi.apis.companies.service.CompanySearchService;
import com.concertticketing.sellerapi.apis.concerts.domain.Concert;
import com.concertticketing.sellerapi.apis.concerts.domain.ConcertConcertCategory;
import com.concertticketing.sellerapi.apis.concerts.domain.ConcertDetailImage;
import com.concertticketing.sellerapi.apis.concerts.dto.AddConcertDto.AddConcertBody;
import com.concertticketing.sellerapi.apis.concerts.dto.AddConcertDto.AddConcertRes;
import com.concertticketing.sellerapi.apis.concerts.dto.ConcertDetailDto.ConcertDetailRes;
import com.concertticketing.sellerapi.apis.concerts.dto.ConcertListDto.ConcertListQuery;
import com.concertticketing.sellerapi.apis.concerts.dto.ConcertListDto.ConcertListRes;
import com.concertticketing.sellerapi.apis.concerts.dto.UpdateConcertDto.UpdateConcertBody;
import com.concertticketing.sellerapi.apis.concerts.mapper.ConcertConcertCategoryMapper;
import com.concertticketing.sellerapi.apis.concerts.mapper.ConcertDetailImageMapper;
import com.concertticketing.sellerapi.apis.concerts.mapper.ConcertMapper;
import com.concertticketing.sellerapi.apis.concerts.service.ConcertConcertCategoryCreateService;
import com.concertticketing.sellerapi.apis.concerts.service.ConcertConcertCategoryDeleteService;
import com.concertticketing.sellerapi.apis.concerts.service.ConcertCreateService;
import com.concertticketing.sellerapi.apis.concerts.service.ConcertDeleteService;
import com.concertticketing.sellerapi.apis.concerts.service.ConcertDetailImageCreateService;
import com.concertticketing.sellerapi.apis.concerts.service.ConcertDetailImageDeleteService;
import com.concertticketing.sellerapi.apis.concerts.service.ConcertSearchService;
import com.concertticketing.sellerapi.apis.venues.service.VenueLayoutSearchService;
import com.concertticketing.sellerapi.apis.venues.service.VenueSearchService;
import com.concertticketing.sellerapi.common.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class ConcertUseCase {
    private final ConcertMapper concertMapper;
    private final ConcertDetailImageMapper concertDetailImageMapper;
    private final ConcertConcertCategoryMapper concertConcertCategoryMapper;

    private final CompanySearchService companySearchService;

    private final ConcertCreateService concertCreateService;
    private final ConcertSearchService concertSearchService;
    private final ConcertDeleteService concertDeleteService;

    private final ConcertDetailImageCreateService concertDetailImageCreateService;
    private final ConcertDetailImageDeleteService concertDetailImageDeleteService;

    private final ConcertConcertCategoryCreateService concertConcertCategoryCreateService;
    private final ConcertConcertCategoryDeleteService concertConcertCategoryDeleteService;

    private final VenueSearchService venueSearchService;

    private final VenueLayoutSearchService venueLayoutSearchService;

    private final int LIST_PAGE_SIZE = 10;

    @Transactional
    public AddConcertRes addConcert(Integer companyId, AddConcertBody body) {
        Concert concert = concertCreateService.save(
            concertMapper.toConcert(
                body,
                companySearchService.getReference(companyId),
                venueSearchService.getReference(body.venueId()),
                venueLayoutSearchService.getReference(body.venueLayoutId())
            )
        );

        if (!CollectionUtils.isEmpty(body.categoryIds())) {
            concertConcertCategoryCreateService.bulkInsert(
                concertConcertCategoryMapper.toConcertConcertCategoryDtos(concert.getId(), body.categoryIds())
            );
        }

        if (!CollectionUtils.isEmpty(body.detailImageUrls())) {
            concertDetailImageCreateService.bulkInsert(
                concertDetailImageMapper.toConcertDetailImageDtos(concert.getId(), body.detailImageUrls())
            );
        }

        return new AddConcertRes(concert.getId());
    }

    @Transactional(readOnly = true)
    public ConcertListRes getConcerts(Integer companyId, ConcertListQuery query) {
        Page<Concert> concerts = concertSearchService.findAllWithVenue(
            companyId,
            PageRequest.of(query.getPageablePage(), LIST_PAGE_SIZE)
        );

        return new ConcertListRes(
            query.page(),
            concerts.getTotalPages(),
            concertMapper.toConcertListItems(concerts.getContent())
        );
    }

    @Transactional(readOnly = true)
    public ConcertDetailRes getConcert(Long concertId, Integer companyId) {
        return concertMapper.toConcertDetailDto(
            concertSearchService.findWithVenueAndVenueLayoutAndCategories(concertId, companyId)
        );
    }

    @Transactional
    public void updateConcert(Long concertId, Integer companyId, UpdateConcertBody body) {
        Concert concert = concertSearchService.findWithVenueAndVenueLayoutAndCategories(concertId, companyId);

        // concert update
        concert.update(
            body.title(),
            body.duration(),
            body.description(),
            body.thumbnail(),
            body.startedAt(),
            body.endedAt(),
            body.bookingStartedAt(),
            body.bookingEndedAt(),
            companySearchService.getReference(companyId),
            venueSearchService.getReference(body.venueId()),
            venueLayoutSearchService.getReference(body.venueLayoutId())
        );

        // concert_concert_category delete & create
        // 영속성 컨텍스트 초기화
        List<Long> deletedConcertCategoryIds = concert.getConcertCategories().stream()
            .map(ConcertConcertCategory::getId)
            .toList();
        List<Long> deletedConcertDetailImageIds = concert.getDetailImages().stream()
            .map(ConcertDetailImage::getId)
            .toList();

        concertConcertCategoryDeleteService.delete(deletedConcertCategoryIds);
        if (!CollectionUtils.isEmpty(body.categoryIds())) {
            concertConcertCategoryCreateService.bulkInsert(
                concertConcertCategoryMapper.toConcertConcertCategoryDtos(concertId, body.categoryIds())
            );
        }

        concertDetailImageDeleteService.delete(deletedConcertDetailImageIds);
        if (!CollectionUtils.isEmpty(body.detailImageUrls())) {
            concertDetailImageCreateService.bulkInsert(
                concertDetailImageMapper.toConcertDetailImageDtos(concertId, body.detailImageUrls())
            );
        }
    }

    public void deleteConcert(Long concertId, Integer companyId) {
        concertDeleteService.deleteConcert(concertId, companyId);
    }
}
