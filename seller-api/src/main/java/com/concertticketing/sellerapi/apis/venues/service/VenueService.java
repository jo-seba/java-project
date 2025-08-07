package com.concertticketing.sellerapi.apis.venues.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.venues.domain.Venue;
import com.concertticketing.sellerapi.apis.venues.repository.VenueRepository;

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
