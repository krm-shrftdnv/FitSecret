package com.company.fit_secret.controller;

import com.company.fit_secret.config.security.details.UserDetailsImpl;
import com.company.fit_secret.dto.UserDto;
import com.company.fit_secret.model.Metrics;
import com.company.fit_secret.model.Product;
import com.company.fit_secret.service.ProductsServiceImpl;
import com.company.fit_secret.service.interfaces.MetricsService;
import com.company.fit_secret.service.interfaces.ProductsService;
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

        if(metricsService.getUserLastMetrics(user.getUserId()).isPresent()) {
            model.addAttribute("hasMetrics", true);
            Metrics metrics = metricsService.getUserLastMetrics(user.getUserId()).get();
            double activityCoefficient = 0;
            switch (user.getActivity()) {
                case ACTIVE: {
                    activityCoefficient = 1.55;
                    break;
                }
                case PASSIVE: {
                    activityCoefficient = 1.2;
                    break;
                }
                case LOW_ACTIVE: {
                    activityCoefficient = 1.375;
                    break;
                }
                case HIGH_ACTIVE: {
                    activityCoefficient = 1.725;
                    break;
                }
            }
            Double caloriesSum = (88.36 + 13.4 * metrics.getWeight() + 4.8 * metrics.getHeight() - 5.7 * user.getAge()) * activityCoefficient;
            model.addAttribute("caloriesSum", caloriesSum);
        } else {
            model.addAttribute("hasMetrics", false);
            model.addAttribute("noMessageMetrics", "You should enter your metrics to get your maximal sum of calories per day");
        }
        return "food";
    }

}
