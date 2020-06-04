package com.company.fit_secret.service.interfaces;

import com.company.fit_secret.service.exceptions.DuplicateEntryException;
import com.company.fit_secret.service.exceptions.NoMatchException;
import com.company.fit_secret.dto.SignUpDto;

public interface SignUpService {

    void addUser(SignUpDto dto) throws DuplicateEntryException, NoMatchException;

}
