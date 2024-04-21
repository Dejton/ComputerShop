package com.computershop.repository;

import com.computershop.TestBase;
import com.computershop.model.entity.Order;
import com.computershop.model.entity.OrderedProduct;
import com.computershop.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest extends TestBase {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    private Order order;
    private User user;
    private List<OrderedProduct> orderedProducts;
    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();

        user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .login("john_doe")
                .email("john@example.com")
                .address("123 Main Street")
                .role("user")
                .build();
        userRepository.save(user);

        order = Order.builder()
                .user(user)
                .orderedProducts(orderedProducts)
                .deliveryAddress("123 Main Street")
                .totalValue(BigDecimal.valueOf(100))
                .dateOfOrder(LocalDate.now())
                .status("pending")
                .build();
    }

    @DisplayName("testing saving new Order")
    @Test
    void shouldReturnSavedOrder() {
//        given
//        when
        Order savedOrder = orderRepository.save(order);
//        then
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isEqualTo(order.getId());
    }
    @DisplayName("testing deleting order by id")
    @Test
    void shouldDeleteOrderById() {
//        given
        orderRepository.save(order);
//        when
        orderRepository.deleteById(order.getId());
        Optional<Order> foundOrder = orderRepository.findById(order.getId());
//        then
        assertThat(foundOrder).isEmpty();
    }
    @DisplayName("testing finding all orders")
    @Test
    void shouldReturnListOfAllOrders() {
//        given
        orderRepository.save(order);
//        when
        List<Order> orders = orderRepository.findAll();
//        then
        assertThat(orders).isNotEmpty();
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getId()).isEqualTo(order.getId());
    }
    @DisplayName("testing finding order by id")
    @Test
    void shouldReturnOrderById() {
//        given
        orderRepository.save(order);
//        when
        Optional<Order> foundOrder = orderRepository.findById(order.getId());
//        then
        assertThat(foundOrder).isNotNull();
        assertThat(foundOrder.get().getId()).isEqualTo(order.getId());
    }
    @DisplayName("testing finding order by id when does not exist")
    @Test
    void shouldReturnEmptyOptional() {
//        given
//        when
        Optional<Order> foundOrder = orderRepository.findById(1L);
//        then
        assertThat(foundOrder).isEmpty();
    }
}

















