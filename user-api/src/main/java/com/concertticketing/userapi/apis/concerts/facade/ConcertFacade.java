package com.concertticketing.userapi.apis.concerts.facade;

import static com.concertticketing.commonkafka.KafkaTopic.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.concertticketing.commonavro.ConcertDetailRequestedEvent;
import com.concertticketing.commonutils.TimeUtils;
import com.concertticketing.domainrdb.domain.concert.domain.Concert;
import com.concertticketing.domainrdb.domain.concert.dto.ConcertListDto;
import com.concertticketing.domainrdb.domain.venue.domain.VenueArea;
import com.concertticketing.domainredis.domain.concert.domain.ConcertListCache;
import com.concertticketing.userapi.apis.concerts.dto.ConcertDetailDto;
import com.concertticketing.userapi.apis.concerts.dto.ConcertsDto;
import com.concertticketing.userapi.apis.concerts.mapper.ConcertMapper;
import com.concertticketing.userapi.apis.concerts.service.ConcertCacheService;
import com.concertticketing.userapi.apis.concerts.service.ConcertService;
import com.concertticketing.userapi.apis.venues.service.VenueAreaService;
import com.concertticketing.userapi.common.annotation.Facade;
import com.concertticketing.userapi.common.kafka.KafkaProducer;

import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class ConcertFacade {
    private final KafkaProducer kafkaProducer;

    private final ConcertMapper concertMapper;

    private final ConcertService concertService;

    private final ConcertCacheService concertCacheService;

    private final VenueAreaService venueAreaService;

    private final int LIST_PAGE_SIZE = 10;

    @Transactional(readOnly = true)
    public ConcertDetailDto.ConcertDetailRes getConcert(Long concertId, Long userId) {
        Concert detailConcert = concertService.findConcert(concertId);
        List<VenueArea> areas = venueAreaService.findAreas(detailConcert.getVenueLayout().getId());

        if (userId != null) {
            kafkaProducer.send(
                CONCERT_DETAIL_REQUESTED_EVENT,
                new ConcertDetailRequestedEvent(
                    concertId,
                    userId,
                    TimeUtils.epochSeconds()
                )
            );
        }

        return concertMapper.toConcertDetailDto(detailConcert, areas);
    }

    public ConcertsDto.ConcertListRes getConcerts(ConcertsDto.ConcertListQuery query) {
        List<ConcertListCache> concertCaches = concertCacheService.getConcerts(query.getConcertSort());
        if (!CollectionUtils.isEmpty(concertCaches)) {
            return new ConcertsDto.ConcertListRes(
                query.page(),
                calculateTotalPages(concertService.getConcertCount()),
                concertMapper.toConcertListDtos(concertCaches)
            );
        }

        Page<ConcertListDto> concerts = concertService.findConcerts(
            query.getConcertSort(),
            PageRequest.of(query.getPageablePage(), LIST_PAGE_SIZE)
        );

        return new ConcertsDto.ConcertListRes(
            query.page(),
            concerts.getTotalPages(),
            concerts.getContent()
        );
    }

    private int calculateTotalPages(Long totalCount) {
        return (int)((totalCount - 1) / LIST_PAGE_SIZE + 1);
    }
}
