package com.company.fit_secret.repository;

import com.company.fit_secret.model.Complex;
import com.company.fit_secret.model.Injury;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ComplexRepository extends JpaRepository<Complex, Long> {

}
