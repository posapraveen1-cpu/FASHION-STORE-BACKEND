package com.fashionstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "cart_items", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}))
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(nullable = false)
    private int quantity;

    protected CartItem() {}
    public CartItem(User user, Product product, int quantity) { this.user = user; this.product = product; this.quantity = quantity; }
    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void addQuantity(int quantity) { this.quantity += quantity; }
}
