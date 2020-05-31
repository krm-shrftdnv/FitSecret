package com.company.fit_secret.repository;

import com.company.fit_secret.model.Metrics;
import com.company.fit_secret.model.id.MetricsId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MetricsRepository extends JpaRepository<Metrics, MetricsId> {

    Optional<List<Metrics>> findAllByUserId(Long userId);
    Optional<Metrics> findFirstByUserIdOrderByDateAsc(Long userId);
    Optional<Metrics> findFirstByUserIdOrderByDateDesc(Long userId);

}
