package com.company.fit_secret.service.interfaces;

import com.company.fit_secret.model.Complex;

import java.util.List;
import java.util.Optional;

public interface ComplexService {
    Optional<List<Complex>> findAllComplexesForUser(Long userId);
    Optional<Complex> getComplexById(Long complexId);
}
