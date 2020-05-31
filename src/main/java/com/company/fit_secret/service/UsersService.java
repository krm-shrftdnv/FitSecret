package com.company.fit_secret.service;

import com.company.fit_secret.model.Injury;

import java.util.List;

public interface UsersService {
    void saveInjuries(Long userId, String[] injuries);
    List<Injury> getUserInjuries(Long userId);
}
