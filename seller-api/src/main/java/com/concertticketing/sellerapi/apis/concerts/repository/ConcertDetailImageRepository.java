package com.concertticketing.sellerapi.apis.concerts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.concertticketing.sellerapi.apis.concerts.domain.ConcertDetailImage;

public interface ConcertDetailImageRepository extends JpaRepository<ConcertDetailImage, Long> {
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM ConcertDetailImage cdi WHERE cdi.id IN :ids")
    int deleteConcertDetailImages(List<Long> ids);
}
