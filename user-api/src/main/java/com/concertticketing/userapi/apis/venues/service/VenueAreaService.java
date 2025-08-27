package com.concertticketing.userapi.apis.venues.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concertticketing.domainrdb.domain.venue.domain.VenueArea;
import com.concertticketing.domainrdb.domain.venue.repository.VenueAreaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VenueAreaService {
    private final VenueAreaRepository areaRepository;

    @Transactional(readOnly = true)
    public List<VenueArea> findAreas(Long layoutId) {
        return areaRepository.findAreasByLayoutId(layoutId);
    }
}
