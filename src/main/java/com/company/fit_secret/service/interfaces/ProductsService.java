package com.company.fit_secret.service.interfaces;

import com.company.fit_secret.model.Product;
import com.company.fit_secret.model.enums.Activity;

import java.util.List;

public interface ProductsService {
    List<Product> getProductsByCategory(String categoryString);
    double countUserCaloriesSum(Long userId, Activity userActivity, int userAge);
}
