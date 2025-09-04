package com.concertticketing.domainrdb.domain.concert.repository;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConcertJdbcRepository {
    private final String BULK_VIEW_COUNT_UPDATE_QUERY = "UPDATE concert SET view_count = view_count + ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    /**
     * Bulk update the view count for multiple concerts.
     *
     * @param concertIncrementMap key: concertId / value: viewCount to increment
     */
    public void bulkUpdateViewCount(Map<Long, Long> concertIncrementMap) {
        if (concertIncrementMap.isEmpty()) {
            return;
        }

        jdbcTemplate.batchUpdate(BULK_VIEW_COUNT_UPDATE_QUERY, concertIncrementMap.entrySet(), 500,
            (ps, entry) -> {
                ps.setLong(1, entry.getValue());
                ps.setLong(2, entry.getKey());
            });
    }
}
