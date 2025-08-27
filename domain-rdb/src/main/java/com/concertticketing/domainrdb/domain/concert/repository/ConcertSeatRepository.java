package com.concertticketing.domainrdb.domain.concert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concertticketing.domainrdb.domain.concert.domain.ConcertSeat;

public interface ConcertSeatRepository extends JpaRepository<ConcertSeat, Long>, ConcertSeatRepositoryCustom {
}
