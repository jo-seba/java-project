package com.concertticketing.sellerapi.apis.venues.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concertticketing.sellerapi.apis.venues.domain.Venue;

public interface VenueRepository extends JpaRepository<Venue, Integer> {
}
