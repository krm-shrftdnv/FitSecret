package itis_804.fit_secret.controller;

import itis_804.fit_secret.config.security.details.UserDetailsImpl;
import itis_804.fit_secret.dto.UserDto;
import itis_804.fit_secret.service.MetricsService;
import itis_804.fit_secret.service.MetricsServiceImpl;
import itis_804.fit_secret.service.UsersService;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String getProfilePage(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = UserDto.from(userDetails.getUser());

        if(metricsService.getMetricsByUserId(user.getUserId()).size() == 0){
            model.addAttribute("notification", "You should enter your metrics for correct work of our system");
        }

        return "profile";
    }

}
