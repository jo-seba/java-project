package com.concertticketing.domainrdb.domain.concert.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.concertticketing.domainrdb.domain.concert.dto.ConcertConcertCategoryDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConcertConcertCategoryJdbcRepository {
    private final String BULK_INSERT_QUERY = "INSERT INTO concert_concert_category (concert_id, category_id) VALUES (?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public void bulkInsert(List<ConcertConcertCategoryDto> dtos) {
        jdbcTemplate.batchUpdate(BULK_INSERT_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ConcertConcertCategoryDto dto = dtos.get(i);
                ps.setLong(1, dto.concertId());
                ps.setInt(2, dto.categoryId());
            }

            @Override
            public int getBatchSize() {
                return dtos.size();
            }
        });
    }
}
