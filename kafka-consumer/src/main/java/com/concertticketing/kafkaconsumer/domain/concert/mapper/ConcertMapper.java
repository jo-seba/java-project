package com.concertticketing.kafkaconsumer.domain.concert.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.concertticketing.domainrdb.domain.concert.dto.ConcertListDto;
import com.concertticketing.domainredis.domain.concert.domain.ConcertListCache;
import com.concertticketing.kafkaconsumer.common.mapstruct.MapStructBaseConfig;

@Mapper(
    config = MapStructBaseConfig.class
)
public interface ConcertMapper {
    ConcertListCache toConcertListCache(ConcertListDto concert);

    List<ConcertListCache> toConcertListCaches(List<ConcertListDto> concerts);
}
