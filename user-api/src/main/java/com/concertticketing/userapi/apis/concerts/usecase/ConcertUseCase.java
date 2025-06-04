package com.concertticketing.userapi.apis.concerts.usecase;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.userapi.apis.concerts.domain.Concert;
import com.concertticketing.userapi.apis.concerts.dto.ConcertDetailDto.ConcertDetailRes;
import com.concertticketing.userapi.apis.concerts.dto.ConcertListDto;
import com.concertticketing.userapi.apis.concerts.dto.ConcertListDto.ConcertListQuery;
import com.concertticketing.userapi.apis.concerts.dto.ConcertListDto.ConcertListRes;
import com.concertticketing.userapi.apis.concerts.mapper.ConcertMapper;
import com.concertticketing.userapi.apis.concerts.service.ConcertSearchService;
import com.concertticketing.userapi.apis.venues.domain.VenueArea;
import com.concertticketing.userapi.apis.venues.service.VenueAreaSearchService;
import com.concertticketing.userapi.common.annotation.UseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class ConcertUseCase {
    private final ConcertMapper concertMapper;
    private final ConcertSearchService concertSearchService;

    private final VenueAreaSearchService areaSearchService;

    private static final int LIST_PAGE_SIZE = 10;

    @Transactional(readOnly = true)
    public ConcertDetailRes getConcert(Long id) {
        Concert detailConcert = concertSearchService.findConcert(id);
        List<VenueArea> areas = areaSearchService.findAreas(detailConcert.getVenueLayoutId());

        return concertMapper.toDetailDto(detailConcert, areas);
    }

    public ConcertListRes getConcerts(ConcertListQuery query) {
        Page<ConcertListDto.ConcertListItem> concerts = concertSearchService.findConcerts(query.getConcertSortEnum(),
            PageRequest.of(query.getPageablePage(), LIST_PAGE_SIZE));

        return new ConcertListRes(
            query.page(),
            concerts.getTotalPages(),
            concerts.getContent()
        );
    }
}
