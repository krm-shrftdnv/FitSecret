package com.company.fit_secret.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignInController {
    // обрабатывает "/signIn", возвращает страницу авторизации
    @GetMapping("/signIn")
    public String getSignInPage(@RequestParam(name = "error", required = false) String error, ModelMap map) {
        if (error != null) map.put("error", error);
        return "sign_in";
    }
}
