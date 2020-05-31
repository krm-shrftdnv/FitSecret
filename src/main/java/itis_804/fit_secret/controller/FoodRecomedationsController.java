package itis_804.fit_secret.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FoodRecomedationsController {

    @GetMapping("/food")
    public String getFoodPage(){
        return "food";
    }

}
