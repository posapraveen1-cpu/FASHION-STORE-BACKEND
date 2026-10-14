package com.fashionstore.controller;

import com.fashionstore.model.CartItem;
import com.fashionstore.service.CartService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService carts;
    public CartController(CartService carts) { this.carts = carts; }
    @GetMapping public List<CartItem> findMine(Principal principal) { return carts.findByUser(principal.getName()); }
    @PostMapping
    public CartItem add(Principal principal, @RequestBody Map<String, Object> request) {
        Object productId = request.get("productId");
        Object quantity = request.get("quantity");
        if (!(productId instanceof Number) || !(quantity instanceof Number)) {
            throw new IllegalArgumentException("productId and quantity must be numeric");
        }
        return carts.add(principal.getName(), ((Number) productId).longValue(), ((Number) quantity).intValue());
    }
    @DeleteMapping("/{productId}")
    public void remove(Principal principal, @PathVariable Long productId) { carts.remove(principal.getName(), productId); }
    @DeleteMapping public void clear(Principal principal) { carts.clear(principal.getName()); }
}
