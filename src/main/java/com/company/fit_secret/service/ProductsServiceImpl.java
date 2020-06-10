package com.company.fit_secret.service;

import com.company.fit_secret.model.Metrics;
import com.company.fit_secret.model.Product;
import com.company.fit_secret.model.enums.Activity;
import com.company.fit_secret.model.enums.Category;
import com.company.fit_secret.repository.ProductsRepository;
import com.company.fit_secret.service.interfaces.MetricsService;
import com.company.fit_secret.service.interfaces.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    MetricsService metricsService;

    @Override
    public List<Product> getProductsByCategory(String categoryString) {
        Category category = Category.valueOf(categoryString);
        return productsRepository.findAllByCategory(category);
    }

    @Override
    public double countUserCaloriesSum(Long userId, Activity userActivity, int userAge) {
        Metrics metrics = metricsService.getUserLastMetrics(userId).get();
        double activityCoefficient = 0;
        switch (userActivity) {
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
        return (88.36 + 13.4 * metrics.getWeight() + 4.8 * metrics.getHeight() - 5.7 * userAge) * activityCoefficient;
    }

}
