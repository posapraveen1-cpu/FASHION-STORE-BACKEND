package com.fashionstore.controller;

import com.fashionstore.model.Product;
import com.fashionstore.service.ProductService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService products;
    public ProductController(ProductService products) { this.products = products; }
    @GetMapping public List<Product> findAll() { return products.findAll(); }
    @GetMapping("/{id}") public Product findById(@PathVariable Long id) { return products.findById(id); }
}
