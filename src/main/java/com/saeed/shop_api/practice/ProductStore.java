package com.saeed.shop_api.practice;

import java.util.List;
import java.util.Optional;

public interface ProductStore {
    void save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);
}
