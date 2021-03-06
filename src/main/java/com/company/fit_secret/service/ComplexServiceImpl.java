package com.company.fit_secret.service;

import com.company.fit_secret.model.*;
import com.company.fit_secret.model.enums.TypeEnum;
import com.company.fit_secret.repository.ComplexRepository;
import com.company.fit_secret.repository.InjuriesRepository;
import com.company.fit_secret.repository.MetricsRepository;
import com.company.fit_secret.repository.UsersRepository;
import com.company.fit_secret.service.interfaces.ComplexService;
import com.company.fit_secret.service.interfaces.MetricsService;
import com.company.fit_secret.service.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ComplexServiceImpl implements ComplexService {

    @Autowired
    ComplexRepository complexRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    InjuriesRepository injuriesRepository;
    @Autowired
    MetricsRepository metricsRepository;

    @Autowired
    MetricsService metricsService;
    @Autowired
    UsersService usersService;

    // возвращает пользователю все подходящие комплексы
    // по его заболеваниям и типу фигуры
    @Transactional
    @Override
    public Optional<List<Complex>> findAllComplexesForUser(Long userId) {
        User user = usersRepository.findById(userId).get();
        List<Complex> complexesForUser;
        List<Injury> injuries = usersService.getUserInjuries(userId);
        List<Complex> allComplexes = complexRepository.findAll();
        if (injuries.size() == 0 || injuries.get(0).getName().equals("no injuries")) {
            if (metricsService.getUserLastMetrics(user.getUserId()).isPresent()) {
                TypeEnum userBodyType = getUserBodyType(user);
                complexesForUser = findComplexesByType(allComplexes, userBodyType);
                complexesForUser.addAll(findComplexesByInjuries(allComplexes, injuries));
            } else return Optional.empty();
        } else {
            complexesForUser = findComplexesByInjuries(allComplexes, injuries);
        }
        return Optional.of(complexesForUser);
    }

    // возвращает модель комплекса по ID
    @Override
    public Optional<Complex> getComplexById(Long complexId) {
        return complexRepository.findById(complexId);
    }

    // возвращает все комплексы по заданным заболеваниям
    private List<Complex> findComplexesByInjuries(List<Complex> allComplexes, List<Injury> injuries) {
        List<Complex> complexesForUser = new ArrayList<>();
        for (Complex complex : allComplexes) {
            Set<Injury> injurySet = complex.getInjuries();
            for (Injury injury : injurySet) {
                if (injuries.contains(injury)) {
                    complexesForUser.add(complex);
                }
            }
        }
        return complexesForUser;
    }

    // возвращает все комплексы по заданному типу фигуры
    private List<Complex> findComplexesByType(List<Complex> allComplexes, TypeEnum userType) {
        List<Complex> complexesForUser = new ArrayList<>();
        for (Complex complex : allComplexes) {
            Set<Type> types = complex.getTypes();
            for (Type type : types) {
                if (type.getType().equals(userType.name())) complexesForUser.add(complex);
            }
        }
        return complexesForUser;
    }

    // возвращает тип фигуры по его замерам
    private TypeEnum getUserBodyType(User user) {
        Metrics metrics = metricsService.getUserLastMetrics(user.getUserId()).get();
        int og = metrics.getOG();
        int ot = metrics.getOT();
        int ob = metrics.getOB();
        if ((og > ot) && (ob > ot) && (Math.abs(og - ob) < 10)) {
            return TypeEnum.HOURGLASS;
        } else if ((og - ot < 15) && (ob - ot < 15) && (Math.abs(ob - og) < 5)) {
            return TypeEnum.RECTANGLE;
        } else if ((og < ob) && (ob - og > 10) && (ot < og)) {
            return TypeEnum.PEAR;
        } else if ((og > ob) && (og - ob > 10) && (ot < ob)) {
            return TypeEnum.INVERTRIANGLE;
        } else {
            return TypeEnum.ORB;
        }
    }

}
