package com.concertticketing.userapi.apis.concerts.facade;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.concertticketing.domainredis.domain.concert.domain.ConcertListCache;
import com.concertticketing.userapi.apis.concerts.dbdto.ConcertListItemDBDto;
import com.concertticketing.userapi.apis.concerts.domain.Concert;
import com.concertticketing.userapi.apis.concerts.dto.ConcertDetailDto;
import com.concertticketing.userapi.apis.concerts.dto.ConcertListDto;
import com.concertticketing.userapi.apis.concerts.mapper.ConcertMapper;
import com.concertticketing.userapi.apis.concerts.service.ConcertCacheService;
import com.concertticketing.userapi.apis.concerts.service.ConcertService;
import com.concertticketing.userapi.apis.venues.domain.VenueArea;
import com.concertticketing.userapi.apis.venues.service.VenueAreaService;
import com.concertticketing.userapi.common.annotation.Facade;

import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class ConcertFacade {
    private final ConcertMapper concertMapper;

    private final ConcertService concertService;

    private final ConcertCacheService concertCacheService;

    private final VenueAreaService venueAreaService;

    private static final int LIST_PAGE_SIZE = 10;

    @Transactional(readOnly = true)
    public ConcertDetailDto.ConcertDetailRes getConcert(Long id) {
        Concert detailConcert = concertService.findConcert(id);
        List<VenueArea> areas = venueAreaService.findAreas(detailConcert.getVenueLayout().getId());

        return concertMapper.toConcertDetailDto(detailConcert, areas);
    }

    public ConcertListDto.ConcertListRes getConcerts(ConcertListDto.ConcertListQuery query) {
        List<ConcertListCache> concertCaches = concertCacheService.getConcerts(query.getConcertSort());
        if (!CollectionUtils.isEmpty(concertCaches)) {
            return new ConcertListDto.ConcertListRes(
                query.page(),
                calculateTotalPages(concertService.getConcertCount()),
                concertMapper.toConcertListItemDBDtos(concertCaches)
            );
        }

        Page<ConcertListItemDBDto> concerts = concertService.findConcerts(
            query.getConcertSort(),
            PageRequest.of(query.getPageablePage(), LIST_PAGE_SIZE)
        );

        return new ConcertListDto.ConcertListRes(
            query.page(),
            concerts.getTotalPages(),
            concerts.getContent()
        );
    }

    private int calculateTotalPages(Long totalCount) {
        return (int)((totalCount - 1) / LIST_PAGE_SIZE + 1);
    }
}
