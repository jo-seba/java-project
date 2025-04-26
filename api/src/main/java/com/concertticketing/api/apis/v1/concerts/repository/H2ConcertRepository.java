package com.concertticketing.api.apis.v1.concerts.repository;

import com.concertticketing.api.apis.v1.concerts.domain.Concert;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class H2ConcertRepository implements ConcertRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<Concert> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Concert.class, id));
    }

    @Override
    public List<Concert> findAll(int page, int size) {
        return entityManager.createQuery(
                        "select c from Concert c order by c.id desc",
                        Concert.class
                )
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }
}

