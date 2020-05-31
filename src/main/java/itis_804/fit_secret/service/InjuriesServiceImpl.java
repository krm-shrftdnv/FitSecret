package itis_804.fit_secret.service;

import itis_804.fit_secret.model.Injury;
import itis_804.fit_secret.model.User;
import itis_804.fit_secret.repository.InjuriesRepository;
import itis_804.fit_secret.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InjuriesServiceImpl implements InjuriesService {

    @Autowired
    InjuriesRepository injuriesRepository;

    @Override
    public List<Injury> getAllInjuries() {
        return injuriesRepository.findAll();
    }

}
