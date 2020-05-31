package itis_804.fit_secret.controller;

import itis_804.fit_secret.dto.SignUpDto;
import itis_804.fit_secret.service.SignUpService;
import itis_804.fit_secret.service.exceptions.DuplicateEntryException;
import itis_804.fit_secret.service.exceptions.NoMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {

    @Autowired
    SignUpService signUpService;

    @PreAuthorize("permitAll()")
    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "sign_up";
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/signUp")
    public String signUp(SignUpDto dto, Model model) {
        try {
            signUpService.addUser(dto);
            return "redirect:/signIn";
        } catch (DuplicateEntryException | NoMatchException e) {
            model.addAttribute("message", e.getMessage());
            return "sign_up";
        }
    }

}
