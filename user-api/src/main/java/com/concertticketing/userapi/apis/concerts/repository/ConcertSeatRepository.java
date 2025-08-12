package com.concertticketing.userapi.apis.concerts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concertticketing.userapi.apis.concerts.domain.ConcertSeat;

public interface ConcertSeatRepository extends JpaRepository<ConcertSeat, Long>, ConcertSeatRepositoryCustom {
}
