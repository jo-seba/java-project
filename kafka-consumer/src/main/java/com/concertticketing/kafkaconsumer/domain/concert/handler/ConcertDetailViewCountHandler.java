package com.concertticketing.kafkaconsumer.domain.concert.handler;

import static com.concertticketing.commonkafka.KafkaTopic.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerFactoryName.*;
import static com.concertticketing.kafkaconsumer.common.constant.ConsumerGroupID.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.concertticketing.commonavro.ConcertDetailRequestedEvent;
import com.concertticketing.kafkaconsumer.common.handler.auto.ConsumerAutoHandler;
import com.concertticketing.kafkaconsumer.domain.concert.mapper.ConcertViewCountMapper;
import com.concertticketing.kafkaconsumer.domain.concert.service.ConcertService;
import com.concertticketing.kafkaconsumer.domain.concert.service.ConcertViewCountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertDetailViewCountHandler
    extends ConsumerAutoHandler<List<ConcertDetailRequestedEvent>> {
    private final ConcertViewCountMapper concertViewCountMapper;

    private final ConcertService concertService;

    private final ConcertViewCountService concertViewCountService;

    @Override
    @KafkaListener(
        topics = {CONCERT_DETAIL_REQUESTED_EVENT},
        groupId = CONCERT_DETAIL_VIEW_COUNT,
        containerFactory = BATCH_AUTO_ACK_CONSUMER_FACTORY
    )
    public void consume(List<ConcertDetailRequestedEvent> events) {
        handleEvent(events);
    }

    @Override
    public void handler(List<ConcertDetailRequestedEvent> events) {
        if (CollectionUtils.isEmpty(events)) {
            return;
        }

        concertService.updateConcertsViewCount(
            events.stream()
                .collect(
                    Collectors.groupingBy(
                        ConcertDetailRequestedEvent::getConcertId,
                        Collectors.counting()
                    )
                )
        );

        concertViewCountService.bulkInsertViewCount(
            events.stream()
                .map(concertViewCountMapper::toConcertViewCount)
                .toList()
        );
    }
}
