package com.computershop.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empty-order")
public class EmptyOrderController {
    @GetMapping
    public String showEmptyOrderPage(Model model) {
        return "emptyOrder";
    }
}
