package com.computershop.computershop.service;

import com.computershop.computershop.entity.Order;
import com.computershop.computershop.entity.OrderedProduct;
import com.computershop.computershop.entity.User;
import com.computershop.computershop.entity.dto.OrderDto;
import com.computershop.computershop.repository.OrderRepository;
import com.computershop.computershop.repository.UserRepository;
import com.computershop.computershop.service.impl.OrderServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private OrderServiceImpl orderService = new OrderServiceImpl(orderRepository);
    private Order order;
    private OrderDto orderDto;
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
        orderDto = OrderDto.mapToDto(order);
    }
    @DisplayName("testing adding new order")
    @Test
    void shouldReturnSavedOrder() {
//        given
        when(orderRepository.save(order)).thenReturn(order);
//        when
        Order savedOrder = orderService.addOrder(orderDto);
//        then
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isEqualTo(order.getId());
    }
    @DisplayName("testing deleting order by id")
    @Test
    void shouldDeleteOrderById() {
//        given
        when(orderRepository.save(order)).thenReturn(order);
//        when
        orderService.deleteOrderById(order.getId());
//        then
        verify(orderRepository).deleteById(order.getId());
    }
    @DisplayName("testing finding all products")
    @Test
    void shouldReturnListOfAllOrders() {
//        given
        when(orderRepository.findAll()).thenReturn(List.of(order));
//        when
        List<OrderDto> orders = orderService.getAllOrders();
//        then
        assertThat(orders).isNotEmpty();
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getId()).isEqualTo(order.getId());
    }
    @DisplayName("testing finding order by id")
    @Test
    void shouldReturnOrderById() {
//        given
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
//        when
        OrderDto foundOrder = orderService.getOrderById(order.getId());
//        then
        assertThat(foundOrder).isNotNull();
        assertThat(foundOrder.getId()).isEqualTo(order.getId());
    }
    @DisplayName("testing finding order by id when does not exist")
    @Test
    void shouldThrowExceptionWhenOrderDoesNotExist() {
//        given
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
//        when
//        then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> orderService.getOrderById(order.getId()));
        assertEquals("Order with id: " + order.getId() + "not found!", exception.getMessage());
    }
    @DisplayName("testing finding order by id when argument is illegal")
    @Test
    void shouldThrowExceptionWhenArgumentIsIllegal() {
//        given
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
//        when
//        then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderService.getOrderById(-10));
        assertEquals("Enter correct value!", exception.getMessage());
    }

}