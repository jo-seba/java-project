package com.concertticketing.userapi.apis.concerts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.concertticketing.userapi.apis.concerts.domain.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long>, ConcertRepositoryCustom {
    @Query("SELECT c FROM Concert c JOIN FETCH c.venue JOIN FETCH c.detailImages WHERE c.id = :concertId")
    Optional<Concert> findConcert(@Param("concertId") Long concertId);
}
