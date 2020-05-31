package com.company.fit_secret.service;

import com.company.fit_secret.repository.InjuriesRepository;
import com.company.fit_secret.model.Injury;
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
