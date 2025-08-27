package com.concertticketing.domainrdb.domain.concert.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.concertticketing.domainrdb.domain.concert.dto.ConcertDetailImageDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConcertDetailImageJdbcRepository {
    private static final String BULK_INSERT_QUERY = "INSERT INTO concert_detail_image (concert_id, image_url) VALUES (?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public void bulkInsert(List<ConcertDetailImageDto> dtos) {
        jdbcTemplate.batchUpdate(BULK_INSERT_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ConcertDetailImageDto dto = dtos.get(i);
                ps.setLong(1, dto.concertId());
                ps.setString(2, dto.imageUrl());
            }

            @Override
            public int getBatchSize() {
                return dtos.size();
            }
        });
    }
}
