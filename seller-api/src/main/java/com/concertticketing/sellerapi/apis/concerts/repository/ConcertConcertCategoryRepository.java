package com.concertticketing.sellerapi.apis.concerts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.concertticketing.sellerapi.apis.concerts.domain.ConcertConcertCategory;

public interface ConcertConcertCategoryRepository extends JpaRepository<ConcertConcertCategory, Long> {
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM ConcertConcertCategory ccc WHERE ccc.id IN :ids")
    int deleteConcertConcertCategories(List<Long> ids);
}
