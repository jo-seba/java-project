package com.concertticketing.domainrdb.domain.venue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concertticketing.domainrdb.domain.venue.domain.Venue;

public interface VenueRepository extends JpaRepository<Venue, Integer> {
}
