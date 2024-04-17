package com.computershop.controller.web;

import com.computershop.model.dto.OrderDto;
import com.computershop.model.entity.User;
import com.computershop.service.OrderService;
import com.computershop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrderPageController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderPageController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public String showOrderPage(Model model, Principal principal) {
        User user = userService.getUserByLogin(principal.getName());
        OrderDto order = orderService.getOrderByUserAndStatus(user, "In progress");

        model.addAttribute("order", order);
        return "orderPage";
    }
}
