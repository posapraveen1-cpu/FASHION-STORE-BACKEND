package com.fashionstore.service;

import com.fashionstore.dto.OrderRequest;
import com.fashionstore.exception.ResourceNotFoundException;
import com.fashionstore.model.CustomerOrder;
import com.fashionstore.model.OrderItem;
import com.fashionstore.model.Product;
import com.fashionstore.repository.CartRepository;
import com.fashionstore.repository.OrderRepository;
import com.fashionstore.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orders;
    private final ProductRepository products;
    private final CartRepository carts;
    private final UserService users;
    public OrderService(OrderRepository orders, ProductRepository products, CartRepository carts, UserService users) {
        this.orders = orders; this.products = products; this.carts = carts; this.users = users;
    }
    @Transactional
    public CustomerOrder create(String email, OrderRequest request) {
        BigDecimal total = BigDecimal.ZERO;
        CustomerOrder order = new CustomerOrder(users.getByEmail(email), request.customerName().trim(),
                request.email().trim().toLowerCase(), request.shippingAddress().trim(), request.phone().trim(), total);
        for (OrderRequest.Item requested : request.items()) {
            Product product = products.findById(requested.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + requested.productId()));
            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(requested.quantity()));
            total = total.add(lineTotal);
            order.addItem(new OrderItem(product, requested.quantity(), product.getPrice()));
        }
        order.setTotal(total);
        CustomerOrder saved = orders.saveAndFlush(order);
        carts.deleteByUserEmail(email);
        return saved;
    }
    public List<CustomerOrder> findMine(String email) { return orders.findByUserEmailOrderByCreatedAtDesc(email); }
    public List<CustomerOrder> findAll() { return orders.findAllByOrderByCreatedAtDesc(); }
    @Transactional
    public CustomerOrder updateStatus(Long id, String status) {
        if (!List.of("PLACED", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED").contains(status)) {
            throw new IllegalArgumentException("Unsupported order status");
        }
        CustomerOrder order = orders.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        order.setStatus(status);
        return order;
    }
}
