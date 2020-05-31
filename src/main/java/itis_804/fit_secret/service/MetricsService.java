package itis_804.fit_secret.service;

import itis_804.fit_secret.dto.MetricsDto;
import itis_804.fit_secret.model.Metrics;

import java.util.List;
import java.util.Optional;

public interface MetricsService {

    void addMetrics(Long id, MetricsDto dto);
    List<Metrics> getMetricsByUserId(Long userId);
    Optional<Metrics> getUserLastMetrics(Long userId);
    Optional<Metrics> getUserFirstMetrics(Long userId);

}
