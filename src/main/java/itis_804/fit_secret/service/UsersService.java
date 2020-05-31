package itis_804.fit_secret.service;

import itis_804.fit_secret.model.Injury;
import itis_804.fit_secret.model.Metrics;

import java.util.List;
import java.util.Set;

public interface UsersService {
    void saveInjuries(Long userId, String[] injuries);
    List<Injury> getUserInjuries(Long userId);
}
