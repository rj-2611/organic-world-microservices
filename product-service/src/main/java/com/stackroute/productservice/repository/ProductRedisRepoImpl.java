package com.stackroute.productservice.repository;

import com.stackroute.productservice.model.Product;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRedisRepoImpl implements ProductRedisRepo {
    private RedisTemplate<String, Product> redisTemplate;
    private HashOperations hashOperations; //to access Redis cache
    public ProductRedisRepoImpl(RedisTemplate<String, Product> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveProduct(Product product) {
        hashOperations.put("products",product.getId(),product);
    }

    @Override
    public void saveCategory(String category, List<Product> productList) {
        hashOperations.put("product-category",category,productList);
    }

    @Override
    public void deleteCategory(String category) {
        hashOperations.delete("product-category",category);
    }

}
