package com.computershop.computershop.repository;

import com.computershop.computershop.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    private Order order;
    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();

    }
}