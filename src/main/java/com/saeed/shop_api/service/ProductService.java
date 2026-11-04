package com.saeed.shop_api.service;

import com.saeed.shop_api.entity.Product;
import com.saeed.shop_api.exception.ProductNotFoundException;
import com.saeed.shop_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    // update و delete را خودت بنویس ↓
    public Product update(Long id, Product updated) {
        Product existing = findById(id);
        existing.setName(updated.getName());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        existing.setDescription(updated.getDescription());
        return productRepository.save(existing);
    }
    public void delete(Long id) {
        findById(id);
        productRepository.deleteById(id);
    }
}
