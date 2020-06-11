package com.company.fit_secret.service;

import com.company.fit_secret.repository.MetricsRepository;
import com.company.fit_secret.repository.UsersRepository;
import com.company.fit_secret.dto.MetricsDto;
import com.company.fit_secret.model.Metrics;
import com.company.fit_secret.service.interfaces.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MetricsServiceImpl implements MetricsService {

    @Autowired
    MetricsRepository metricsRepository;
    @Autowired
    UsersRepository usersRepository;

    // записывает в бд данные о замерах пользователя
    @Override
    public void addMetrics(Long id, MetricsDto dto) {
        Metrics metrics = Metrics.builder()
                .userId(id)
                .date(LocalDateTime.now())
                .height(dto.getHeight())
                .weight(dto.getWeight())
                .OB(dto.getOB())
                .OG(dto.getOG())
                .OT(dto.getOT())
                .build();
        metricsRepository.save(metrics);
    }

    // возвращает замеры пользователя за всё время
    @Override
    public List<Metrics> getMetricsByUserId(Long userId) {
        List<Metrics> userMetrics = new ArrayList<>();
        if (metricsRepository.findAllByUserId(userId).isPresent()) {
            userMetrics = metricsRepository.findAllByUserId(userId).get();
        }
        return userMetrics;
    }

    // возвращает последние замеры пользователя
    @Override
    public Optional<Metrics> getUserLastMetrics(Long userId) {
        return metricsRepository.findFirstByUserIdOrderByDateDesc(userId);
    }

    // возвращает первые замеры пользователя
    @Override
    public Optional<Metrics> getUserFirstMetrics(Long userId) {
        return metricsRepository.findFirstByUserIdOrderByDateAsc(userId);
    }

}
