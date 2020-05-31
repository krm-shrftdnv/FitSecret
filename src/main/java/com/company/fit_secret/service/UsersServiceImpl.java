package com.company.fit_secret.service;

import com.company.fit_secret.repository.InjuriesRepository;
import com.company.fit_secret.repository.UsersRepository;
import com.company.fit_secret.model.Injury;
import com.company.fit_secret.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.company.fit_secret.model.Injury.from;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    InjuriesRepository injuriesRepository;
    @Autowired
    UsersRepository usersRepository;

    @Transactional
    @Override
    public void saveInjuries(Long userId, String[] injuries) {
        User user = usersRepository.findById(userId).get();
        Set<Injury> injurySet = from(injuries);
        user.setInjuries(injurySet);
        usersRepository.save(user);

        for(String injuryString : injuries) {
            Injury injury = injuriesRepository.findByName(injuryString).get();
            Set<User> injuredUsers = injury.getInjuredUsers();
            injuredUsers.add(user);
            injury.setInjuredUsers(injuredUsers);
            injuriesRepository.save(injury);
        }
    }

    @Transactional
    @Override
    public List<Injury> getUserInjuries(Long userId) {
        User user = usersRepository.findById(userId).get();
        return new ArrayList<>(user.getInjuries());
    }

}
