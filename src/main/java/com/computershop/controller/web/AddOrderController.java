package com.computershop.controller.web;

import com.computershop.model.dto.ProductIdAndQuantityDto;
import com.computershop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddOrderController {
    private  final OrderService orderService;

    public AddOrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/add-order")
    public String addProduct(@ModelAttribute ProductIdAndQuantityDto productIdDto, Model model) {
        Long userId = 1L;
        orderService.addProductToOrder(userId,productIdDto.getId(), productIdDto.getQuantity());
        return "redirect:/";
    }
}
