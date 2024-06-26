package com.computershop.service;

import com.computershop.model.dto.OrderDto;
import com.computershop.model.entity.*;
import com.computershop.repository.OrderRepository;
import com.computershop.repository.ProductRepository;
import com.computershop.repository.UserRepository;
import com.computershop.service.impl.OrderServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private ProductRepository productRepository = mock(ProductRepository.class);
    private OrderServiceImpl orderService = new OrderServiceImpl(orderRepository, productRepository, userRepository);
    private Order order;
    private OrderDto orderDto;
    private User user;
    private Product product;
    private Category category;
    private Producer producer;
    private List<OrderedProduct> orderedProducts;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();

        product = Product.builder()
                .id(1L)
                .name("Laptop Dell XPS 13")
                .description("Powerful and portable laptop with stunning display.")
                .price(new BigDecimal("1999.99"))
                .category(category)
                .producer(producer)
                .amountInMagazine(10)
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .build();
        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .login("john_doe")
                .email("john@example.com")
                .address("123 Main Street")
                .role("user")
                .build();
        userRepository.save(user);

        order = Order.builder()
                .id(1L)
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

    @DisplayName("testing adding new product to order")
    @Test
    void shouldReturnPositiveResponse() {
//        given
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
//        when
        ResponseEntity<String> response = orderService.addProductToOrder(order.getId(), product.getId(), 1);
//        then
        assertThat(response).isEqualTo(ResponseEntity.ok("Product successfully added to your order!"));
    }

    @DisplayName("testing adding new product to order when there is no product")
    @Test
    void shouldReturnMessageThatThereIsNoProduct() {
//        given
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
//        when
        ResponseEntity<String> response = orderService.addProductToOrder(order.getId(), product.getId(), 1);
//        then
        assertThat(response).isEqualTo(ResponseEntity.badRequest().body("Product with id: " + product.getId() + " not found!"));
    }

    @DisplayName("testing adding new product to order when there is no order")
    @Test
    void shouldReturnMessageThatThereIsNoOrder() {
//        given
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
//        when
        ResponseEntity<String> response = orderService.addProductToOrder(user.getId(), product.getId(), 1);
//        then
        assertThat(response).isEqualTo(ResponseEntity.ok("Product successfully added to your order!"));
    }

    @DisplayName("testing adding product to order If there are not enough products in magazine")
    @Test
    void shouldReturnMessageIfThereAreNotEnoughProducts() {
//        given
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
//        when
        ResponseEntity<String> response = orderService.addProductToOrder(order.getId(), product.getId(), 11);
//        then
        assertThat(response).isEqualTo(ResponseEntity.badRequest().body("Not enough products in magazine!"));
    }

    @DisplayName("testing changing status is order")
    @Test
    void shouldReturnPositiveResponseAboutStatusChanging() {
//        given
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
//        when
        ResponseEntity<String> response = orderService.updateOrderStatus(order.getId(), "updatedStatus");
//        then
        assertThat(response).isEqualTo(ResponseEntity.ok("The order status has been successfully updated."));
    }

    @DisplayName("testing changing status if there is no order")
    @Test
    void shouldReturnMessageIfThereIsNoOrder() {
//        given
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
//        when
        ResponseEntity<String> response = orderService.updateOrderStatus(order.getId(), "updatedStatus");
//        then
        assertThat(response).isEqualTo(ResponseEntity.badRequest().body("Order with id: " + order.getId() + " not found!"));
    }
//    --------------------------------------

    @Test
    void addProductToOrder_ProductNotFound_ReturnsBadRequest() {
        // Given
        Long userId = 1L;
        Long productId = 1L;
        int quantity = 2;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<String> response = orderService.addProductToOrder(userId, productId, quantity);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("Product with id: " + productId + " not found!");
    }

    @Test
    void addProductToOrder_NotEnoughProductsInMagazine_ReturnsBadRequest() {
        // Given
        Long userId = 1L;
        Long productId = 1L;
        int quantity = 2;
        Product product = new Product();
        product.setAmountInMagazine(1); // Only 1 available
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        ResponseEntity<String> response = orderService.addProductToOrder(userId, productId, quantity);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("Not enough products in magazine!");
    }

    @Test
    void addProductToOrder_UserNotFound_ReturnsBadRequest() {
        // Given
        Long userId = 1L;
        Long productId = 1L;
        int quantity = 2;
        Product product = new Product();
        product.setAmountInMagazine(10);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<String> response = orderService.addProductToOrder(userId, productId, quantity);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("User with id: " + userId + " not found!");
    }


    @Test
    void addProductToOrder_NoOrderInProgress_NewOrderCreated() {
        // Given

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(orderRepository.findByUserAndStatus(eq(user), eq("In progress"))).thenReturn(Optional.empty());

        // When
        ResponseEntity<String> response = orderService.addProductToOrder(user.getId(), product.getId(), 5);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        // Additional assertions can be made based on the behavior of the method
    }

}