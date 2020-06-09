package com.company.fit_secret.controller;

import com.company.fit_secret.config.security.details.UserDetailsImpl;
import com.company.fit_secret.dto.MetricsDto;
import com.company.fit_secret.dto.ReturningMetricsDto;
import com.company.fit_secret.dto.UserDto;
import com.company.fit_secret.model.Metrics;
import com.company.fit_secret.service.interfaces.MetricsService;
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
            model.addAttribute("message", "У вас пока нет текущих замеров");
            model.addAttribute("link", "Введите первые!");
        } else {
            //check that last metrics update was more than week ago
            if(metricsService.getUserLastMetrics(user.getUserId()).isPresent()) {
                LocalDateTime afterLastUpdate = metricsService.getUserLastMetrics(user.getUserId()).get().getDate().plusDays(7);
                if (afterLastUpdate.isAfter(LocalDateTime.now())) {
                    model.addAttribute("needMetrics", false);
                    model.addAttribute("message", "Вы должны будете обновить замеры " + afterLastUpdate.getDayOfMonth() + " " + afterLastUpdate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
                }
                else {
                    model.addAttribute("needMetrics", true);
                    model.addAttribute("message", "Обновите Ваши замеры.");
                }
            }
            Metrics firstMetrics = metricsService.getUserFirstMetrics(user.getUserId()).get();
            ReturningMetricsDto firstMetricsDto = ReturningMetricsDto.from(firstMetrics);
            model.addAttribute("firstMetrics", firstMetricsDto);
            if(metricsService.getUserLastMetrics(user.getUserId()).isPresent()) {
                Metrics lastMetrics = metricsService.getUserLastMetrics(user.getUserId()).get();
                ReturningMetricsDto lastMetricsDto = ReturningMetricsDto.from(lastMetrics);
                model.addAttribute("hasLast", true);
                model.addAttribute("lastMetrics", lastMetricsDto);
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
                model.addAttribute("message", "Вы должны будете обновить замеры " + afterLastUpdate.getDayOfMonth() + " " + afterLastUpdate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
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
