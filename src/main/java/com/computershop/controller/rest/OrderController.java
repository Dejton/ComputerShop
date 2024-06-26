package com.computershop.controller.rest;

import com.computershop.model.entity.Order;
import com.computershop.model.dto.OrderDto;
import com.computershop.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order addOrder(@RequestBody OrderDto orderDto) {
        return orderService.addOrder(orderDto);
    }
    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }
    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable long id) {
        return orderService.getOrderById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable long id) {
        orderService.deleteOrderById(id);
    }
    @PostMapping("/order/addproduct")
    public ResponseEntity<String> addProductToOrder(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
       return orderService.addProductToOrder(userId,productId,quantity);
    }

}

