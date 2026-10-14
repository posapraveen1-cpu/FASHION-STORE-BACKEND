package com.fashionstore.controller;

import com.fashionstore.dto.ProductRequest;
import com.fashionstore.model.CustomerOrder;
import com.fashionstore.model.Product;
import com.fashionstore.service.OrderService;
import com.fashionstore.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final ProductService products;
    private final OrderService orders;
    public AdminController(ProductService products, OrderService orders) {
        this.products = products; this.orders = orders;
    }
    @GetMapping("/orders") public List<CustomerOrder> findOrders() { return orders.findAll(); }
    @PostMapping("/products") public Product createProduct(@Valid @RequestBody ProductRequest request) { return products.create(request); }
    @PutMapping("/products/{id}") public Product updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return products.update(id, request);
    }
    @DeleteMapping("/products/{id}") public void deleteProduct(@PathVariable Long id) { products.delete(id); }
    @PutMapping("/orders/{id}/status")
    public CustomerOrder updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        if (status == null) throw new IllegalArgumentException("status is required");
        return orders.updateStatus(id, status);
    }
}
