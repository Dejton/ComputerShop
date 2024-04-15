package com.computershop.controller.web;

import com.computershop.model.dto.ProductIdAndQuantityDto;
import com.computershop.service.OrderService;
import com.computershop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AddOrderController {
    private  final OrderService orderService;
    private final UserService userService;

    public AddOrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }
    @PostMapping("/add-order")
    public String addProduct(@ModelAttribute ProductIdAndQuantityDto productIdDto, Principal principal) {
        long userId = userService.getUserByLogin(principal.getName()).getId();
        orderService.addProductToOrder(userId,productIdDto.getId(), productIdDto.getQuantity());
        return "redirect:/";
    }
}
