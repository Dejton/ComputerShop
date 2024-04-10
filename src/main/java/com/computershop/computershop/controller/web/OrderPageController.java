package com.computershop.computershop.controller.web;

import com.computershop.computershop.entity.User;
import com.computershop.computershop.entity.dto.OrderDto;
import com.computershop.computershop.entity.dto.OrderedProductDto;
import com.computershop.computershop.entity.dto.UserDto;
import com.computershop.computershop.service.OrderService;
import com.computershop.computershop.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showOrderPage(Model model) {
        User user = UserDto.mapFromDto(userService.getUserById(1L));
        List<OrderedProductDto> orderedProducts = orderService.getAllProductsInCart(user, "In progress");
        OrderDto order;

        try {
            order = orderService.getOrderByUserAndStatus(user, "In progress");
        } catch (EntityNotFoundException e) {
            // Jeśli nie ma zamówienia dla użytkownika w danym statusie, przekieruj na "emptyOrder"
            return "redirect:/emptyOrder";
        }

        model.addAttribute("products", orderedProducts);
        model.addAttribute("order", order);

        // Jeśli istnieje zamówienie dla użytkownika w danym statusie, przekieruj na "orderPage"
        return "orderPage";
    }
}
