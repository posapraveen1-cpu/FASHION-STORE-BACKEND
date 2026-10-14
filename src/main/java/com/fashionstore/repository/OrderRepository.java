package com.fashionstore.repository;

import com.fashionstore.model.CustomerOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByUserEmailOrderByCreatedAtDesc(String email);
    List<CustomerOrder> findAllByOrderByCreatedAtDesc();
}
