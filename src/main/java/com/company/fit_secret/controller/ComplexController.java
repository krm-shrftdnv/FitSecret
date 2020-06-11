package com.company.fit_secret.controller;

import com.company.fit_secret.config.security.details.UserDetailsImpl;
import com.company.fit_secret.dto.ComplexDto;
import com.company.fit_secret.dto.UserDto;
import com.company.fit_secret.model.Complex;
import com.company.fit_secret.service.interfaces.ComplexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.company.fit_secret.dto.ComplexDto.from;

@Controller
public class ComplexController {

    @Autowired
    ComplexService complexService;

    // обрабатывает "/training", выводит все подходящие пользователю комплексы,
    // если таких не имеется, предложит заполнить данные о себе или ждать новых комплексов
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/training")
    public String getTrainingPage(Authentication authentication, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = UserDto.from(userDetails.getUser());
        if(complexService.findAllComplexesForUser(user.getUserId()).isPresent()) {
            model.addAttribute("hasComplexes", true);
            List<Complex> userComplexes = complexService.findAllComplexesForUser(user.getUserId()).get();
            model.addAttribute("complexes", userComplexes);
        } else {
            model.addAttribute("hasComplexes", false);
        }
        return "training";
    }

    // обрабатывает "/complex{complex-id}", где {complex-id} - число, ID комплекса
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/complex{complex-id}")
    public String getComplexPage(Model model, @PathVariable(name = "complex-id")String complexId){
        Long id = Long.valueOf(complexId);
        Optional<Complex> complexOptional = complexService.getComplexById(id);
        if(complexOptional.isPresent()){
            Complex complex = complexOptional.get();
            String complexDescription = readFileLineByLine(complex.getFileName());
            ComplexDto complexDto = from(complex, complexDescription);
            model.addAttribute("complex", complexDto);
        }
        return "complex";
    }

    // метод считывания файла в строку, используется для чтения файлов с комплексами
    private static String readFileLineByLine(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

}
