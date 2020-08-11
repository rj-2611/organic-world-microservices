package com.stackroute.productservice.repository;

import com.stackroute.productservice.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductRedisRepo {
    void saveProduct(Product product);
    void saveCategory(String category, List<Product> productList);
    void deleteCategory(String category);
}
