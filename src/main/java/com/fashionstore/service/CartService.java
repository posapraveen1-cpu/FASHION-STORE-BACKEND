package com.fashionstore.service;

import com.fashionstore.exception.ResourceNotFoundException;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Product;
import com.fashionstore.repository.CartRepository;
import com.fashionstore.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    private final CartRepository carts;
    private final ProductRepository products;
    private final UserService users;
    public CartService(CartRepository carts, ProductRepository products, UserService users) {
        this.carts = carts; this.products = products; this.users = users;
    }
    public List<CartItem> findByUser(String email) { return carts.findByUserEmail(email); }
    @Transactional
    public CartItem add(String email, Long productId, int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be at least one");
        Product product = products.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));
        return carts.findByUserEmailAndProductId(email, productId).map(item -> {
            item.addQuantity(quantity); return item;
        }).orElseGet(() -> carts.save(new CartItem(users.getByEmail(email), product, quantity)));
    }
    public void remove(String email, Long productId) { carts.deleteByUserEmailAndProductId(email, productId); }
    public void clear(String email) { carts.deleteByUserEmail(email); }
}
