package com.company.fit_secret.service;

import com.company.fit_secret.model.Product;
import com.company.fit_secret.model.enums.Category;
import com.company.fit_secret.repository.ProductsRepository;
import com.company.fit_secret.service.interfaces.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    ProductsRepository productsRepository;

    @Override
    public List<Product> getProductsByCategory(String categoryString) {
        Category category = Category.valueOf(categoryString);
        return productsRepository.findAllByCategory(category);
    }

}
