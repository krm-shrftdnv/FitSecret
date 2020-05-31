package itis_804.fit_secret.service;

import itis_804.fit_secret.dto.SignUpDto;
import itis_804.fit_secret.service.exceptions.DuplicateEntryException;
import itis_804.fit_secret.service.exceptions.NoMatchException;

public interface SignUpService {

    void addUser(SignUpDto dto) throws DuplicateEntryException, NoMatchException;

}
