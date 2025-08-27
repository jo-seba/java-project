package com.concertticketing.sellerapi.apis.venues.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.domainrdb.domain.venue.domain.Venue;
import com.concertticketing.domainrdb.domain.venue.repository.VenueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenueService {
    private final VenueRepository venueRepository;

    // Read
    @Transactional(readOnly = true)
    public Venue getVenueReference(Integer venueId) {
        return venueRepository.getReferenceById(venueId);
    }
}
