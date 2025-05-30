package com.concertticketing.api.apis.venues.repository;

import com.concertticketing.api.apis.venues.domain.VenueArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueAreaRepository extends JpaRepository<VenueArea, Long> {
    List<VenueArea> findAreasByLayoutId(Long layoutId);
}
