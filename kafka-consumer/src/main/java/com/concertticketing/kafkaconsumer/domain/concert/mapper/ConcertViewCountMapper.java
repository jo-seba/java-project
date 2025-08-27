package com.concertticketing.kafkaconsumer.domain.concert.mapper;

import org.mapstruct.Mapper;

import com.concertticketing.commonavro.ConcertDetailRequestedEvent;
import com.concertticketing.domainmongodb.domain.concertViewCount.domain.ConcertViewCount;
import com.concertticketing.kafkaconsumer.common.mapstruct.MapStructBaseConfig;
import com.concertticketing.kafkaconsumer.common.mapstruct.TimeMapStructHelper;

@Mapper(
    config = MapStructBaseConfig.class,
    uses = {
        TimeMapStructHelper.class
    }
)
public interface ConcertViewCountMapper {
    ConcertViewCount toConcertViewCount(ConcertDetailRequestedEvent event);
}
