package itis_804.fit_secret.controller;

import itis_804.fit_secret.config.security.details.UserDetailsImpl;
import itis_804.fit_secret.dto.MetricsDto;
import itis_804.fit_secret.dto.UserDto;
import itis_804.fit_secret.model.Metrics;
import itis_804.fit_secret.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Controller
public class MetricsController {

    @Autowired
    MetricsService metricsService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/metrics")
    public String getMetricsPage(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = UserDto.from(userDetails.getUser());
        List<Metrics> userMetrics = metricsService.getMetricsByUserId(user.getUserId());
        if (userMetrics.size() == 0) {
            model.addAttribute("noMetrics", true);
            model.addAttribute("message", "You haven't get any metrics yet.");
            model.addAttribute("link", "Go to fill the first ones.");
        } else {
            Metrics firstMetrics = metricsService.getUserFirstMetrics(user.getUserId()).get();
            model.addAttribute("firstMetrics", firstMetrics);
            if(metricsService.getUserLastMetrics(user.getUserId()).isPresent()) {
                Metrics lastMetrics = metricsService.getUserLastMetrics(user.getUserId()).get();
                model.addAttribute("hasLast", true);
                model.addAttribute("lastMetrics", lastMetrics);
            }
        }
        return "metrics";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/metrics/form")
    public String getMetricsForm(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = UserDto.from(userDetails.getUser());
        //check that last metrics update was more than week ago
        if(metricsService.getUserLastMetrics(user.getUserId()).isPresent()) {
            LocalDateTime afterLastUpdate = metricsService.getUserLastMetrics(user.getUserId()).get().getDate().plusDays(7);
            if (afterLastUpdate.isAfter(LocalDateTime.now()))
                model.addAttribute("message", "You should enter your new metrics on " + afterLastUpdate.getDayOfMonth() + " of " + afterLastUpdate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        }
        return "metrics_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/metrics/form")
    public String postMetricsForm(Authentication authentication, MetricsDto dto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = UserDto.from(userDetails.getUser());
        metricsService.addMetrics(user.getUserId(), dto);
        return "redirect:/metrics";
    }

}
