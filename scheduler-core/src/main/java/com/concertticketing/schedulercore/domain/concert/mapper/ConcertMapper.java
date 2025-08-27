package com.concertticketing.schedulercore.domain.concert.mapper;

import org.mapstruct.Mapper;

import com.concertticketing.schedulercore.common.mapstruct.MapStructBaseConfig;
import com.concertticketing.schedulercore.domain.concert.domain.Concert;
import com.concertticketing.schedulercore.domain.concert.domain.ConcertTicketingConfig;
import com.concertticketing.schedulercore.domain.concert.dto.BookableConcertDto;
import com.concertticketing.schedulercore.domain.concert.dto.ConcertTicketingConfigDto;

@Mapper(
    config = MapStructBaseConfig.class
)
public interface ConcertMapper {
    BookableConcertDto toBookableConcertDto(Concert concert);

    ConcertTicketingConfigDto toConcertTicketingConfigDto(ConcertTicketingConfig concert);
}
