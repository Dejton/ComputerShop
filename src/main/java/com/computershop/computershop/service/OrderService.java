package com.computershop.computershop.service;

import com.computershop.computershop.entity.Order;
import com.computershop.computershop.entity.User;
import com.computershop.computershop.entity.dto.OrderDto;
import com.computershop.computershop.entity.dto.OrderedProductDto;
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

}
