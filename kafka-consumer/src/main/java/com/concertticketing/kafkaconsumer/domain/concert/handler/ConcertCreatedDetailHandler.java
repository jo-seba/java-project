package com.concertticketing.kafkaconsumer.domain.concert.handler;

import static com.concertticketing.commonkafka.KafkaTopic.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerFactoryName.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerGroupID.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.concertticketing.commonavro.ConcertCreatedEvent;
import com.concertticketing.kafkaconsumer.common.constant.ConcertSort;
import com.concertticketing.kafkaconsumer.common.handler.auto.ConsumerAutoHandler;
import com.concertticketing.kafkaconsumer.domain.concert.dto.ConcertListItemDBDto;
import com.concertticketing.kafkaconsumer.domain.concert.mapper.ConcertMapper;
import com.concertticketing.kafkaconsumer.domain.concert.service.ConcertCacheService;
import com.concertticketing.kafkaconsumer.domain.concert.service.ConcertService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertCreatedDetailHandler
    extends ConsumerAutoHandler<ConcertCreatedEvent> {
    private final ConcertMapper concertMapper;

    private final ConcertService concertService;

    private final ConcertCacheService concertCacheService;

    private final int LIST_PAGE_SIZE = 10;

    @Override
    @KafkaListener(
        topics = {CONCERT_CREATED},
        groupId = CONCERT_CREATED_DETAIL,
        containerFactory = SINGLE_AUTO_ACK_CONSUMER_FACTORY
    )
    public void consume(ConcertCreatedEvent event) {
        handleEvent(event);
    }

    @Override
    public void handler(ConcertCreatedEvent event) {
        for (ConcertSort sort : ConcertSort.values()) {
            Page<ConcertListItemDBDto> concerts = concertService.findConcerts(
                sort,
                PageRequest.of(0, LIST_PAGE_SIZE)
            );
            concertCacheService.setConcerts(
                sort,
                concertMapper.toConcertListCaches(concerts.getContent())
            );
        }
    }
}
