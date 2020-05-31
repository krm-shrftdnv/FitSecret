package itis_804.fit_secret.repository;

import itis_804.fit_secret.model.Injury;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InjuriesRepository extends JpaRepository<Injury, Long> {

    Optional<Injury> findByName(String name);

}
