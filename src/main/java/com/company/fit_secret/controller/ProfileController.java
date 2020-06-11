package com.company.fit_secret.controller;

import com.company.fit_secret.config.security.details.UserDetailsImpl;
import com.company.fit_secret.dto.UserDto;
import com.company.fit_secret.service.interfaces.MetricsService;
import com.company.fit_secret.service.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    UsersService usersService;
    @Autowired
    MetricsService metricsService;

    // обрабатывает "/profile", возвращает страницу profile с ссылками на другие страницы сайта,
    // если у пользователя не заполнены данные, выдаст предупреждение
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String getProfilePage(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = UserDto.from(userDetails.getUser());

        if(metricsService.getMetricsByUserId(user.getUserId()).size() == 0){
            model.addAttribute("notification", "Вы должны ввести Ваши замеры для корректной работы системы");
        }
        return "profile";
    }

}
