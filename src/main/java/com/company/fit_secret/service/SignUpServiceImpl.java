package com.company.fit_secret.service;

import com.company.fit_secret.repository.UsersRepository;
import com.company.fit_secret.service.exceptions.DuplicateEntryException;
import com.company.fit_secret.service.exceptions.NoMatchException;
import com.company.fit_secret.dto.SignUpDto;
import com.company.fit_secret.model.User;
import com.company.fit_secret.service.interfaces.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsersRepository usersRepository;

    // добавляет пользователя в базу данных,
    // предварительно проверив на уже существующую почту и несовпадение паролей
    @Override
    public void addUser(SignUpDto dto) throws DuplicateEntryException, NoMatchException {
        if (usersRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateEntryException("User with such email already exists");
        }
        if (!dto.getPassword().equals(dto.getRepeated())) {
            throw new NoMatchException("Passwords should match");
        }
        User user = User.builder()
                .fullName(dto.getFullName())
                .age(dto.getAge())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        usersRepository.save(user);
    }
}
