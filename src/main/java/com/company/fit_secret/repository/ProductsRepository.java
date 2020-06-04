package com.company.fit_secret.repository;

import com.company.fit_secret.model.Product;
import com.company.fit_secret.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(Category category);
}
