package com.computershop.computershop.controller.web;

import com.computershop.computershop.entity.Order;
import com.computershop.computershop.entity.OrderedProduct;
import com.computershop.computershop.entity.User;
import com.computershop.computershop.entity.dto.*;
import com.computershop.computershop.service.CategoryService;
import com.computershop.computershop.service.OrderService;
import com.computershop.computershop.service.ProductService;
import com.computershop.computershop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class HomePageController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;


    public HomePageController(CategoryService categoryService, ProductService productService, OrderService orderService, UserService userService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        User user = UserDto.mapFromDto(userService.getUserById(1L));
        List<CategoryDto> categories = categoryService.getAllCategories();
        List<ProductDto> products = productService.getAllProducts();
        List<OrderedProductDto> orderedProducts = orderService.getAllProductsInCart(user, "In progress");
        BigDecimal totalAmount = orderedProducts.stream()
                .map(OrderedProductDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        List<ProductDto> sortedProducts = products.stream()
                .sorted(Comparator.comparingLong(ProductDto::getId).reversed())
                .toList();

        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("orderedProducts", orderedProducts);
        model.addAttribute("categories", categories);
        model.addAttribute("products", sortedProducts);
        return "index";
    }
    @PostMapping("/add-to-cart")
    public String addProductToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        orderService.addProductToOrder(userId,productId,quantity);
        return "redirect:/";
    }
}
