package com.concertticketing.sellerapi.apis.concerts.facade;

import static com.concertticketing.commonkafka.KafkaTopic.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.concertticketing.commonavro.ConcertCreatedEvent;
import com.concertticketing.domainrdb.domain.concert.domain.Concert;
import com.concertticketing.domainrdb.domain.concert.domain.ConcertConcertCategory;
import com.concertticketing.domainrdb.domain.concert.domain.ConcertDetailImage;
import com.concertticketing.sellerapi.apis.companies.service.CompanyService;
import com.concertticketing.sellerapi.apis.concerts.dto.AddConcertDto;
import com.concertticketing.sellerapi.apis.concerts.dto.ConcertDetailDto;
import com.concertticketing.sellerapi.apis.concerts.dto.ConcertListDto;
import com.concertticketing.sellerapi.apis.concerts.dto.UpdateConcertDto;
import com.concertticketing.sellerapi.apis.concerts.mapper.ConcertConcertCategoryMapper;
import com.concertticketing.sellerapi.apis.concerts.mapper.ConcertDetailImageMapper;
import com.concertticketing.sellerapi.apis.concerts.mapper.ConcertMapper;
import com.concertticketing.sellerapi.apis.concerts.service.ConcertConcertCategoryService;
import com.concertticketing.sellerapi.apis.concerts.service.ConcertDetailImageService;
import com.concertticketing.sellerapi.apis.concerts.service.ConcertService;
import com.concertticketing.sellerapi.apis.venues.service.VenueLayoutService;
import com.concertticketing.sellerapi.apis.venues.service.VenueService;
import com.concertticketing.sellerapi.common.annotation.Facade;
import com.concertticketing.sellerapi.common.kafka.KafkaProducer;

import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class ConcertFacade {
    private final KafkaProducer kafkaProducer;

    private final ConcertMapper concertMapper;
    private final ConcertDetailImageMapper concertDetailImageMapper;
    private final ConcertConcertCategoryMapper concertConcertCategoryMapper;

    private final CompanyService companyService;

    private final ConcertService concertService;

    private final ConcertDetailImageService concertDetailImageService;

    private final ConcertConcertCategoryService concertConcertCategoryService;

    private final VenueService venueService;

    private final VenueLayoutService venueLayoutService;

    private final int LIST_PAGE_SIZE = 10;
    private final int CONCERT_CREATED_EVENT_MAX_RETRY_COUNT = 5;

    @Transactional
    public AddConcertDto.AddConcertRes addConcert(Integer companyId, AddConcertDto.AddConcertBody body) {
        Concert concert = concertService.saveConcert(
            concertMapper.toConcert(
                body,
                companyService.getCompanyReference(companyId),
                venueService.getVenueReference(body.venueId()),
                venueLayoutService.getVenueLayoutReference(body.venueLayoutId())
            )
        );

        if (!CollectionUtils.isEmpty(body.categoryIds())) {
            concertConcertCategoryService.bulkInsertConcertConcertCategories(
                concertConcertCategoryMapper.toConcertConcertCategoryDtos(concert.getId(), body.categoryIds())
            );
        }

        if (!CollectionUtils.isEmpty(body.detailImageUrls())) {
            concertDetailImageService.bulkInsertConcertDetailImages(
                concertDetailImageMapper.toConcertDetailImageDtos(concert.getId(), body.detailImageUrls())
            );
        }

        kafkaProducer.send(
            CONCERT_CREATED,
            new ConcertCreatedEvent(
                concert.getId(),
                0,
                CONCERT_CREATED_EVENT_MAX_RETRY_COUNT
            )
        );

        return new AddConcertDto.AddConcertRes(concert.getId());
    }

    @Transactional(readOnly = true)
    public ConcertListDto.ConcertListRes getConcerts(Integer companyId, ConcertListDto.ConcertListQuery query) {
        Page<Concert> concerts = concertService.findConcertsWithVenue(
            companyId,
            PageRequest.of(query.getPageablePage(), LIST_PAGE_SIZE)
        );

        return new ConcertListDto.ConcertListRes(
            query.page(),
            concerts.getTotalPages(),
            concertMapper.toConcertListItems(concerts.getContent())
        );
    }

    @Transactional(readOnly = true)
    public ConcertDetailDto.ConcertDetailRes getConcert(Long concertId, Integer companyId) {
        return concertMapper.toConcertDetailDto(
            concertService.findConcert(concertId, companyId)
        );
    }

    @Transactional
    public void updateConcert(Long concertId, Integer companyId, UpdateConcertDto.UpdateConcertBody body) {
        Concert concert = concertService.findConcert(concertId, companyId);

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
            companyService.getCompanyReference(companyId),
            venueService.getVenueReference(body.venueId()),
            venueLayoutService.getVenueLayoutReference(body.venueLayoutId())
        );

        // concert_concert_category delete & create
        // 영속성 컨텍스트 초기화
        List<Long> deletedConcertCategoryIds = concert.getConcertCategories().stream()
            .map(ConcertConcertCategory::getId)
            .toList();
        List<Long> deletedConcertDetailImageIds = concert.getDetailImages().stream()
            .map(ConcertDetailImage::getId)
            .toList();

        concertConcertCategoryService.deleteConcertConcertCategories(deletedConcertCategoryIds);
        if (!CollectionUtils.isEmpty(body.categoryIds())) {
            concertConcertCategoryService.bulkInsertConcertConcertCategories(
                concertConcertCategoryMapper.toConcertConcertCategoryDtos(concertId, body.categoryIds())
            );
        }

        concertDetailImageService.deleteConcertDetailImages(deletedConcertDetailImageIds);
        if (!CollectionUtils.isEmpty(body.detailImageUrls())) {
            concertDetailImageService.bulkInsertConcertDetailImages(
                concertDetailImageMapper.toConcertDetailImageDtos(concertId, body.detailImageUrls())
            );
        }
    }

    public void deleteConcert(Long concertId, Integer companyId) {
        concertService.deleteConcert(concertId, companyId);
    }
}
