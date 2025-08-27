package com.concertticketing.domainrdb.domain.venue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concertticketing.domainrdb.domain.venue.domain.VenueLayout;

public interface VenueLayoutRepository extends JpaRepository<VenueLayout, Long> {
}
