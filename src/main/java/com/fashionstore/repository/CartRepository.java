package com.fashionstore.repository;

import com.fashionstore.model.CartItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserEmail(String email);
    Optional<CartItem> findByUserEmailAndProductId(String email, Long productId);
    void deleteByUserEmailAndProductId(String email, Long productId);
    void deleteByUserEmail(String email);
}
