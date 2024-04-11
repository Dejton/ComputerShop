package com.computershop.computershop.controller.web;

import com.computershop.computershop.entity.User;
import com.computershop.computershop.entity.dto.OrderDto;
import com.computershop.computershop.entity.dto.UserDto;
import com.computershop.computershop.service.OrderService;
import com.computershop.computershop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RealizeOrderController {
    private final OrderService orderService;
    private final UserService userService;

    public RealizeOrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/realize-order")
    public String realizeOrder() {
        Long userId = 1L;
        User user = UserDto.mapFromDto(userService.getUserById(userId));
        OrderDto order = orderService.getOrderByUserAndStatus(user, "In progress");
        orderService.realizeOrder(order.getId());
        return "redirect:/";
    }
}
