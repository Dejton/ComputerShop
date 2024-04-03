package com.computershop.computershop.service;

import com.computershop.computershop.entity.Order;
import com.computershop.computershop.entity.dto.OrderDto;

import java.util.List;

public interface OrderService {
    Order addOrder(OrderDto orderDto);
    void deleteOrderById(long id);
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(long id);
}
