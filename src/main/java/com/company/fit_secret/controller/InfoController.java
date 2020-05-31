package com.company.fit_secret.controller;

import com.company.fit_secret.config.security.details.UserDetailsImpl;
import com.company.fit_secret.dto.UserDto;
import com.company.fit_secret.model.Injury;
import com.company.fit_secret.model.Metrics;
import com.company.fit_secret.service.InjuriesService;
import com.company.fit_secret.service.MetricsService;
import com.company.fit_secret.service.UsersService;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/info")
    public String getInfoPage(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = UserDto.from(userDetails.getUser());
        model.addAttribute("fullName", user.getFullName());
        model.addAttribute("age", user.getAge());
        model.addAttribute("email", user.getEmail());
        if (usersService.getUserInjuries(user.getUserId()).size() != 0 ) {
            model.addAttribute("hasInjuries", true);
            model.addAttribute("injuries", usersService.getUserInjuries(user.getUserId()));
        }
        else {
            model.addAttribute("hasInjuries", false);
            model.addAttribute("injuriesMessage", "You should select your injuries or match that you haven't any.");
            List<Injury> injuries = injuriesService.getAllInjuries();
            model.addAttribute("injuriesList", injuries);
        }

        Optional<Metrics> lastMetrics = metricsService.getUserLastMetrics(user.getUserId());
        if (lastMetrics.isPresent()) {
            model.addAttribute("hasMetrics", true);
            model.addAttribute("height", lastMetrics.get().getHeight());
            model.addAttribute("OG", lastMetrics.get().getOG());
            model.addAttribute("OT", lastMetrics.get().getOT());
            model.addAttribute("OB", lastMetrics.get().getOB());
        } else {
            model.addAttribute("hasMetrics", false);
            model.addAttribute("metricsMessage", "You should enter your metrics for correct work of our system");
        }
        return "info";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/saveInjuries")
    public String saveInjuries(Authentication authentication, @RequestParam(name = "injuries[]") String[] injuries) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = UserDto.from(userDetails.getUser());
        usersService.saveInjuries(user.getUserId(), injuries);
        return "redirect:/info";
    }

}
