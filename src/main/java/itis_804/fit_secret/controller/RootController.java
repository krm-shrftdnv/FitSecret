package itis_804.fit_secret.controller;

import itis_804.fit_secret.config.security.details.UserDetailsImpl;
import itis_804.fit_secret.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String getRootPage(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/profile";
        } else return "redirect:/signIn";
    }

}
