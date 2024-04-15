package com.computershop.controller.web;

import com.computershop.model.dto.OrderDto;
import com.computershop.model.entity.User;
import com.computershop.service.OrderService;
import com.computershop.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class RealizeOrderController {
    private final OrderService orderService;
    private final UserService userService;

    public RealizeOrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/realize-order")
    @Secured("ROLE_USER")
    public String realizeOrder(Principal principal) {
        User user = userService.getUserByLogin(principal.getName());
        OrderDto order = orderService.getOrderByUserAndStatus(user, "In progress");
        orderService.realizeOrder(order.getId());
        return "redirect:/";
    }
}
