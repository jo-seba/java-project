package com.concertticketing.kafkaconsumer.domain.concert.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.concertticketing.domainredis.domain.concert.domain.ConcertListCache;
import com.concertticketing.kafkaconsumer.common.mapstruct.MapStructBaseConfig;
import com.concertticketing.kafkaconsumer.domain.concert.dto.ConcertListItemDBDto;

@Mapper(
    config = MapStructBaseConfig.class
)
public interface ConcertMapper {
    ConcertListCache toConcertListCache(ConcertListItemDBDto concert);

    List<ConcertListCache> toConcertListCaches(List<ConcertListItemDBDto> concerts);
}
