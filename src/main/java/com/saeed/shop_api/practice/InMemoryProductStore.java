package com.saeed.shop_api.practice;

import java.util.*;

public class InMemoryProductStore implements ProductStore {
    public InMemoryProductStore() {
        super();
    }

    Map<Long, Product> products = new HashMap<Long, Product>();

    @Override
    public void save(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public void deleteById(Long id) {
        products.remove(id);
    }
}
