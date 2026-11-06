package com.saeed.shop_api.service;

import com.saeed.shop_api.dto.ProductRequest;
import com.saeed.shop_api.dto.ProductResponse;
import com.saeed.shop_api.entity.Product;
import com.saeed.shop_api.exception.ProductNotFoundException;
import com.saeed.shop_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ۱. همه محصولات
    public List<ProductResponse> findAll() {
        List<ProductResponse> result = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            result.add(ProductResponse.from(product));
        }
        return result;
    }

    // ۲. یک محصول با id
    public ProductResponse findById(Long id) {
        Product product = getProductOrThrow(id);
        return ProductResponse.from(product);
    }

    // ۳. ساختن محصول جدید
    public ProductResponse create(ProductRequest request) {
        Product product = new Product();               // یک محصول خالی
        product.setName(request.name());               // از فرم به دفتر انبار
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());

        Product saved = productRepository.save(product);   // ذخیره در دیتابیس (id می‌گیرد)
        return ProductResponse.from(saved);                // تبدیل به رسید
    }

    // ۴. به‌روزرسانی
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = getProductOrThrow(id);       // محصول موجود را پیدا کن

        product.setName(request.name());               // داده‌های جدید را بگذار
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());

        Product saved = productRepository.save(product);
        return ProductResponse.from(saved);
    }

    // ۵. حذف
    public void delete(Long id) {
        Product product = getProductOrThrow(id);
        productRepository.delete(product);
    }

    // متد کمکی: پیدا کن یا خطا بده
    private Product getProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}