package com.concertticketing.userapi.apis.concerts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.concertticketing.userapi.apis.concerts.domain.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long>, ConcertRepositoryCustom {
    /**
     * @return concert with Venue, VenueLayout, ConcertCategories and ConcertCategory
     */
    @Query("SELECT c FROM Concert c JOIN FETCH c.venue "
        + "JOIN FETCH c.venueLayout "
        + "LEFT JOIN FETCH c.concertCategories cc LEFT JOIN FETCH cc.category "
        + "WHERE c.id = :id")
    Optional<Concert> findConcertDetail(Long id);
}
