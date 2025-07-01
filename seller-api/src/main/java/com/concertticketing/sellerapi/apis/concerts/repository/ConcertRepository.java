package com.concertticketing.sellerapi.apis.concerts.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.concertticketing.sellerapi.apis.concerts.domain.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    @Query("SELECT c FROM Concert c JOIN FETCH c.venue "
        + "JOIN FETCH c.venueLayout "
        + "JOIN FETCH c.concertCategories cc JOIN FETCH cc.category "
        + "WHERE c.id = :id AND c.company.id = :companyId")
    Optional<Concert> findConcertWithVenueAndVenueLayoutAndCategories(
        Long id,
        Integer companyId
    );

    @Query("SELECT c FROM Concert c JOIN FETCH c.venue WHERE c.company.id = :companyId ORDER BY c.id DESC")
    Page<Concert> findConcertsWithVenue(Integer companyId, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Concert c SET c.isDeleted = TRUE, c.deletedAt = CURRENT_TIMESTAMP WHERE c.id = :id and c.company.id = :companyId")
    void deleteConcertByIdAndCompanyId(
        Long id,
        Integer companyId
    );
}
