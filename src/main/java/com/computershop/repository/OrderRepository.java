package com.computershop.repository;

import com.computershop.model.entity.Order;
import com.computershop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserAndStatus(User user, String status);
}
