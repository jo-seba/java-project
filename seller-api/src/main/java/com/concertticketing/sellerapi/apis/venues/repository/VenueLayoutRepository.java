package com.concertticketing.sellerapi.apis.venues.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concertticketing.sellerapi.apis.venues.domain.VenueLayout;

public interface VenueLayoutRepository extends JpaRepository<VenueLayout, Long> {
}
