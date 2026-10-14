package com.fashionstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_orders")
public class CustomerOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "customer_name", nullable = false, length = 120)
    private String customerName;
    @Column(nullable = false, length = 180)
    private String email;
    @Column(name = "shipping_address", nullable = false, length = 500)
    private String shippingAddress;
    @Column(nullable = false, length = 30)
    private String phone;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    @Column(nullable = false, length = 30)
    private String status = "PLACED";
    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();

    protected CustomerOrder() {}
    public CustomerOrder(User user, String customerName, String email, String shippingAddress, String phone, BigDecimal total) {
        this.user = user; this.customerName = customerName; this.email = email;
        this.shippingAddress = shippingAddress; this.phone = phone; this.total = total;
    }
    public void addItem(OrderItem item) { items.add(item); item.setOrder(this); }
    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getEmail() { return email; }
    public String getShippingAddress() { return shippingAddress; }
    public String getPhone() { return phone; }
    public BigDecimal getTotal() { return total; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public List<OrderItem> getItems() { return items; }
    public void setStatus(String status) { this.status = status; }
    public void setTotal(BigDecimal total) { this.total = total; }
}
