package com.computershop.controller.web;

import com.computershop.model.dto.OrderDto;
import com.computershop.model.dto.OrderedProductDto;
import com.computershop.model.entity.User;
import com.computershop.service.OrderService;
import com.computershop.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

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
        List<OrderedProductDto> products = orderService.getAllProductsInCart(user, "In progress");
        OrderDto order;
        try {
             order = orderService.getOrderByUserAndStatus(user, "In progress");
        } catch (EntityNotFoundException e) {
            return "redirect:/empty-order";
        }

        model.addAttribute("order", order);
        model.addAttribute("products", products);
        return "orderPage";
    }
}
