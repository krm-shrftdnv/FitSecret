package itis_804.fit_secret.service;

import itis_804.fit_secret.dto.MetricsDto;
import itis_804.fit_secret.model.Metrics;
import itis_804.fit_secret.repository.MetricsRepository;
import itis_804.fit_secret.repository.UsersRepository;
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

    @Override
    public void addMetrics(Long id, MetricsDto dto) {
        Metrics metrics = Metrics.builder()
                .userId(id)
                .date(LocalDateTime.now())
                .height(dto.getHeight())
                .OB(dto.getOB())
                .OG(dto.getOG())
                .OT(dto.getOT())
                .build();
        metricsRepository.save(metrics);
    }

    @Override
    public List<Metrics> getMetricsByUserId(Long userId) {
        List<Metrics> userMetrics = new ArrayList<>();
        if (metricsRepository.findAllByUserId(userId).isPresent()) {
            userMetrics = metricsRepository.findAllByUserId(userId).get();
        }
        return userMetrics;
    }

    @Override
    public Optional<Metrics> getUserLastMetrics(Long userId) {
        return metricsRepository.findFirstByUserIdOrderByDateDesc(userId);
    }

    @Override
    public Optional<Metrics> getUserFirstMetrics(Long userId) {
        return metricsRepository.findFirstByUserIdOrderByDateAsc(userId);
    }

}
