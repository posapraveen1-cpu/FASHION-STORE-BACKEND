package com.fashionstore.service;

import com.fashionstore.dto.ProductRequest;
import com.fashionstore.exception.ResourceNotFoundException;
import com.fashionstore.model.Product;
import com.fashionstore.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository products;
    private final CategoryService categories;
    public ProductService(ProductRepository products, CategoryService categories) {
        this.products = products; this.categories = categories;
    }
    public List<Product> findAll() { return products.findAll(); }
    public Product findById(Long id) {
        return products.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }
    public Product create(ProductRequest request) {
        return products.save(new Product(request.name().trim(), request.description(), request.price(),
                request.imageUrl(), categories.getOrCreate(request.category())));
    }
    @Transactional
    public Product update(Long id, ProductRequest request) {
        Product product = findById(id);
        product.update(request.name().trim(), request.description(), request.price(), request.imageUrl(),
                categories.getOrCreate(request.category()));
        return product;
    }
    public void delete(Long id) { products.delete(findById(id)); }
}
