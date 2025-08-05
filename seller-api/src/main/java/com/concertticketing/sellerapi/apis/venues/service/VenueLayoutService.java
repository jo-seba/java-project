package com.concertticketing.sellerapi.apis.venues.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.sellerapi.apis.venues.domain.VenueLayout;
import com.concertticketing.sellerapi.apis.venues.repository.VenueLayoutRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenueLayoutService {
    private final VenueLayoutRepository venueLayoutRepository;

    // Read
    @Transactional(readOnly = true)
    public VenueLayout getVenueLayoutReference(Long venueLayoutId) {
        return venueLayoutRepository.getReferenceById(venueLayoutId);
    }
}
