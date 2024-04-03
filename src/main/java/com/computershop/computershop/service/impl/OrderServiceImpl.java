package com.computershop.computershop.service.impl;

import com.computershop.computershop.entity.Order;
import com.computershop.computershop.entity.dto.OrderDto;
import com.computershop.computershop.repository.OrderRepository;
import com.computershop.computershop.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order addOrder(OrderDto orderDto) {
        return orderRepository.save(OrderDto.mapFromDto(orderDto));
    }

    @Override
    public void deleteOrderById(long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderDto::mapToDto)
                .toList();
    }

    @Override
    public OrderDto getOrderById(long id) {
        if (id < 0) throw new IllegalArgumentException("Enter correct value!");
        return OrderDto.mapToDto(orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order with id: " + id + "not found!")));
    }
}
