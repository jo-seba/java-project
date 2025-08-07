package com.concertticketing.userapi.apis.venues.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concertticketing.userapi.apis.venues.domain.VenueArea;

public interface VenueAreaRepository extends JpaRepository<VenueArea, Long> {
    List<VenueArea> findAreasByLayoutId(Long layoutId);
}
