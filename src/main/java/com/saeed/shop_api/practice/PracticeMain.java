package com.saeed.shop_api.practice;

public class PracticeMain {
    public static void main(String[] args) {
        var memoryStore = new InMemoryProductStore();
        memoryStore.save(new Product(1L, "Laptop", 400_000_000.0, 10));
        memoryStore.save(new Product(2L, "Mouse", 500_000_000.0, 5));
        memoryStore.save(new Product(3L, "Phone", 300_000_000.0, 15));

        memoryStore.findAll().forEach(System.out::println);
        memoryStore.findById(2L).ifPresent(System.out::println);
        try {
            Product p = memoryStore.findById(99L).orElseThrow(() -> new ProductNotFoundException(99L));
        } catch (ProductNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        Product laptop = memoryStore.findById(1L).orElseThrow(() -> new ProductNotFoundException(1L));
        try {
            laptop.decreaseStock(50);
        } catch (OutOfStockException e) {
            System.out.println("Error: " + e.getMessage());
        }
        laptop.decreaseStock(2);
        memoryStore.findAll().forEach(System.out::println);
    }
}
