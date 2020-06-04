package com.company.fit_secret.service.interfaces;

import com.company.fit_secret.model.Product;

import java.util.List;

public interface ProductsService {
    List<Product> getProductsByCategory(String categoryString);
}
