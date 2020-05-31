package com.company.fit_secret.repository;

import com.company.fit_secret.model.Injury;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InjuriesRepository extends JpaRepository<Injury, Long> {

    Optional<Injury> findByName(String name);

}
