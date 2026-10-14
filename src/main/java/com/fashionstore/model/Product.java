package com.fashionstore.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 180)
    private String name;
    @Column(length = 2000)
    private String description;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Column(name = "image_url", length = 1000)
    private String imageUrl;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    protected Product() {}
    public Product(String name, String description, BigDecimal price, String imageUrl, Category category) {
        this.name = name; this.description = description; this.price = price; this.imageUrl = imageUrl; this.category = category;
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    @JsonProperty("category")
    public String getCategory() { return category.getName(); }
    @JsonIgnore
    public Category getCategoryEntity() { return category; }
    public void update(String name, String description, BigDecimal price, String imageUrl, Category category) {
        this.name = name; this.description = description; this.price = price; this.imageUrl = imageUrl; this.category = category;
    }
}
