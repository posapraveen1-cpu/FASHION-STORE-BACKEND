package com.fashionstore.controller;

import com.fashionstore.dto.OrderRequest;
import com.fashionstore.model.CustomerOrder;
import com.fashionstore.service.OrderService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orders;
    public OrderController(OrderService orders) { this.orders = orders; }
    @PostMapping
    public CustomerOrder create(Principal principal, @Valid @RequestBody OrderRequest request) {
        return orders.create(principal.getName(), request);
    }
    @GetMapping("/my")
    public List<CustomerOrder> findMine(Principal principal) { return orders.findMine(principal.getName()); }
}
