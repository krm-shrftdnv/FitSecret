package com.company.fit_secret.controller;

import com.company.fit_secret.config.security.details.UserDetailsImpl;
import com.company.fit_secret.dto.UserDto;
import com.company.fit_secret.model.Injury;
import com.company.fit_secret.model.Metrics;
import com.company.fit_secret.model.enums.Activity;
import com.company.fit_secret.service.interfaces.InjuriesService;
import com.company.fit_secret.service.interfaces.MetricsService;
import com.company.fit_secret.service.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class InfoController {

    @Autowired
    MetricsService metricsService;
    @Autowired
    UsersService usersService;
    @Autowired
    InjuriesService injuriesService;

    // обрабатывает "/info", возвращает страницу с данными о пользователе,
    // если у пользователя не заполнены данные, выдаст предупреждение
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/info")
    public String getInfoPage(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = UserDto.from(userDetails.getUser());
        user = UserDto.from(usersService.getUserById(user.getUserId()));
        model.addAttribute("fullName", user.getFullName());
        model.addAttribute("age", user.getAge());
        model.addAttribute("email", user.getEmail());

        if (user.getActivity() == null) {
            String[] activityStrings = new String[Activity.values().length];
            for (int i = 0; i < Activity.values().length; i++) {
                activityStrings[i] = Activity.values()[i].toString();
            }
            model.addAttribute("hasActivity", false);
            model.addAttribute("activityMessage", "Выберите и сохраните ваш уровень активности");
            model.addAttribute("activities", activityStrings);
        } else {
            model.addAttribute("hasActivity", true);
            model.addAttribute("activity", user.getActivity());
        }

        if (usersService.getUserInjuries(user.getUserId()).size() != 0) {
            model.addAttribute("hasInjuries", true);
            model.addAttribute("injuries", usersService.getUserInjuries(user.getUserId()));
        } else {
            model.addAttribute("hasInjuries", false);
            model.addAttribute("injuriesMessage", "Вы должны выбрать Ваши заболевания или указать, что таковых не имеется.");
            List<Injury> injuries = injuriesService.getAllInjuries();
            model.addAttribute("injuriesList", injuries);
        }

        Optional<Metrics> lastMetrics = metricsService.getUserLastMetrics(user.getUserId());
        if (lastMetrics.isPresent()) {
            model.addAttribute("hasMetrics", true);
            model.addAttribute("height", lastMetrics.get().getHeight());
            model.addAttribute("weight", lastMetrics.get().getWeight());
            model.addAttribute("OG", lastMetrics.get().getOG());
            model.addAttribute("OT", lastMetrics.get().getOT());
            model.addAttribute("OB", lastMetrics.get().getOB());
        } else {
            model.addAttribute("hasMetrics", false);
            model.addAttribute("metricsMessage", "Вы должны ввести Ваши замеры для корректной работы системы.");
        }
        return "info";
    }

    // обрабатывает post-запрос на "/saveInjuries",
    // сохраняет введенные пользователем данные о заболеваниях
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/saveInjuries")
    public String saveInjuries(Authentication authentication, @RequestParam(name = "injuries[]") String[] injuries) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = UserDto.from(userDetails.getUser());
        usersService.saveInjuries(user.getUserId(), injuries);
        return "redirect:/info";
    }

    // обрабатывает post-запрос на "/setActivity",
    // сохраняет введенные пользователем данные об уровне активности
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/setActivity")
    public String setActivityLevel(Authentication authentication, @RequestParam(name = "activity") String activityString) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = UserDto.from(userDetails.getUser());
        user = UserDto.from(usersService.getUserById(user.getUserId()));
        usersService.setActivity(user.getUserId(), activityString);
        return "redirect:/info";
    }

}
