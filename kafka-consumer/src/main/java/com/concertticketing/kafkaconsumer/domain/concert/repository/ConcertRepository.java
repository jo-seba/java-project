package com.concertticketing.kafkaconsumer.domain.concert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concertticketing.kafkaconsumer.domain.concert.domain.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long>, ConcertRepositoryCustom {
}
