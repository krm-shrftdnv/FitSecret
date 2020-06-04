package com.company.fit_secret.service.interfaces;

import com.company.fit_secret.model.Injury;
import com.company.fit_secret.model.User;

import java.util.List;

public interface UsersService {
    void saveInjuries(Long userId, String[] injuries);
    void setActivity(Long userId, String activityString);
    List<Injury> getUserInjuries(Long userId);
    User getUserById(Long userId);
}
