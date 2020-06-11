package com.company.fit_secret.controller;

import com.company.fit_secret.config.security.details.UserDetailsImpl;
import com.company.fit_secret.dto.UserDto;
import com.company.fit_secret.model.Metrics;
import com.company.fit_secret.model.Product;
import com.company.fit_secret.service.ProductsServiceImpl;
import com.company.fit_secret.service.interfaces.MetricsService;
import com.company.fit_secret.service.interfaces.ProductsService;
import com.company.fit_secret.service.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FoodRecommendationsController {

    @Autowired
    ProductsService productsService;
    @Autowired
    MetricsService metricsService;
    @Autowired
    UsersService usersService;

    // обрабатывает "/food", выводит список продуктов и,
    // если у пользователя есть данные о замерах, максимальную сумму калорий в день,
    // иначе выдаст предупреждение
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/food")
    public String getFoodPage(Authentication authentication, Model model){
        model.addAttribute("milkProducts", productsService.getProductsByCategory("MILK"));
        model.addAttribute("cerealProducts", productsService.getProductsByCategory("CEREAL"));
        model.addAttribute("vegetableProducts", productsService.getProductsByCategory("VEGETABLE"));
        model.addAttribute("fruitProducts", productsService.getProductsByCategory("FRUIT"));
        model.addAttribute("meatProducts", productsService.getProductsByCategory("MEAT"));
        model.addAttribute("groceryProducts", productsService.getProductsByCategory("GROCERY"));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto user = UserDto.from(userDetails.getUser());
        user = UserDto.from(usersService.getUserById(user.getUserId()));

        if(metricsService.getUserLastMetrics(user.getUserId()).isPresent()) {
            model.addAttribute("hasMetrics", true);
            double caloriesSum = productsService.countUserCaloriesSum(user.getUserId(), user.getActivity(), user.getAge());
            model.addAttribute("caloriesSum", (int) caloriesSum);
        } else {
            model.addAttribute("hasMetrics", false);
            model.addAttribute("noMessageMetrics", "Вы должны ввести Ваши замеры, чтобы получить максимальную сумму калорий.");
        }
        return "food";
    }

}
