package itis_804.fit_secret.repository;

import itis_804.fit_secret.model.Metrics;
import itis_804.fit_secret.model.User;
import itis_804.fit_secret.model.id.MetricsId;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MetricsRepository extends JpaRepository<Metrics, MetricsId> {

    Optional<List<Metrics>> findAllByUserId(Long userId);
    Optional<Metrics> findFirstByUserIdOrderByDateAsc(Long userId);
    Optional<Metrics> findFirstByUserIdOrderByDateDesc(Long userId);

}
