package com.concertticketing.domainrdb.domain.concert.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.concertticketing.domainrdb.domain.concert.domain.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long>, ConcertRepositoryCustom {
    /**
     * @return concert with Venue, VenueLayout, ConcertCategories and ConcertCategory
     */
    @Query("SELECT c FROM Concert c JOIN FETCH c.venue "
        + "JOIN FETCH c.venueLayout "
        + "LEFT JOIN FETCH c.concertCategories cc LEFT JOIN FETCH cc.category "
        + "WHERE c.id = :id AND c.company.id = :companyId")
    Optional<Concert> findConcert(
        Long id,
        Integer companyId
    );

    /**
     * @return concert with Venue, VenueLayout, ConcertCategories and ConcertCategory
     */
    @Query("SELECT c FROM Concert c JOIN FETCH c.venue "
        + "JOIN FETCH c.venueLayout "
        + "LEFT JOIN FETCH c.concertCategories cc LEFT JOIN FETCH cc.category "
        + "WHERE c.id = :id")
    Optional<Concert> findConcert(
        Long id
    );

    @Query("SELECT c FROM Concert c JOIN FETCH c.venue WHERE c.company.id = :companyId ORDER BY c.id DESC")
    Page<Concert> findConcertsWithVenue(Integer companyId, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Concert c SET c.isDeleted = TRUE, c.deletedAt = CURRENT_TIMESTAMP WHERE c.id = :id and c.company.id = :companyId")
    void deleteConcertByIdAndCompanyId(
        Long id,
        Integer companyId
    );

    /**
     * date 기준으로 예약 가능한 콘서트 추출
     * @param nextDay 당일 + 1
     * @param today 당일
     */
    @Query("SELECT c FROM Concert c "
        + "WHERE c.bookingStartedAt < :nextDay AND c.bookingEndedAt >= :today")
    List<Concert> findBookableConcerts(LocalDateTime today, LocalDateTime nextDay);
}
