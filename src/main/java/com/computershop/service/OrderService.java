package com.computershop.service;

import com.computershop.model.entity.Order;
import com.computershop.model.entity.User;
import com.computershop.model.dto.OrderDto;
import com.computershop.model.dto.OrderedProductDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    Order addOrder(OrderDto orderDto);
    void deleteOrderById(long id);
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(long id);
    ResponseEntity<String> addProductToOrder(Long userId, Long productId, int quantity);
    ResponseEntity<String> updateOrderStatus(Long orderId, String newStatus);
    OrderDto getOrderByUserAndStatus(User user, String status);
    List<OrderedProductDto> getAllProductsInCart(User user, String status);
    void realizeOrder(Long orderId);
}
