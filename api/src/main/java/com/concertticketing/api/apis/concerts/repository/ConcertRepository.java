package com.concertticketing.api.apis.concerts.repository;

import com.concertticketing.api.apis.concerts.domain.Concert;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConcertRepository extends JpaRepository<Concert, Long>, ConcertRepositoryCustom {
    @Query("SELECT c FROM Concert c JOIN FETCH c.venue JOIN FETCH c.detailImages WHERE c.id = :concertId")
    Optional<Concert> findConcert(@Param("concertId") Long concertId);
}
