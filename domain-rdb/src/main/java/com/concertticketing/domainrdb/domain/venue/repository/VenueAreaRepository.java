package com.concertticketing.domainrdb.domain.venue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concertticketing.domainrdb.domain.venue.domain.VenueArea;

public interface VenueAreaRepository extends JpaRepository<VenueArea, Long> {
    List<VenueArea> findAreasByLayoutId(Long layoutId);
}
