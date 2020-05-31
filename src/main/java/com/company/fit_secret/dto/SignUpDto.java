package com.company.fit_secret.dto;

import lombok.Data;

@Data
public class SignUpDto {

    private String fullName;
    private int age;
    private String email;
    private String password;
    private String repeated;

}
