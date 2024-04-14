package com.computershop.controller.web;

import com.computershop.model.entity.User;
import com.computershop.model.dto.CategoryDto;
import com.computershop.model.dto.OrderedProductDto;
import com.computershop.model.dto.ProductDto;
import com.computershop.model.dto.UserDto;
import com.computershop.service.CategoryService;
import com.computershop.service.OrderService;
import com.computershop.service.ProductService;
import com.computershop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Controller
public class ProductsByCategoryController {
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    public ProductsByCategoryController(CategoryService categoryService, UserService userService, ProductService productService, OrderService orderService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/products/{category}")
    public String showProductsByCategory(@PathVariable String category, Model model) {
        User user = UserDto.mapFromDto(userService.getUserById(1L));
        List<CategoryDto> categories = categoryService.getAllCategories();
        List<ProductDto> products = productService.getAllProducts();
        List<ProductDto> productsByCategory = products.stream()
                .filter(productDto -> productDto.getCategory().getName().equals(category))
                .toList();
        System.out.println(productsByCategory);
        List<OrderedProductDto> orderedProducts = orderService.getAllProductsInCart(user, "In progress");
        BigDecimal totalAmount = orderedProducts.stream()
                .map(OrderedProductDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        List<ProductDto> sortedProducts = products.stream()
                .sorted(Comparator.comparingLong(ProductDto::getId).reversed())
                .toList();

        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("productsByCategory", productsByCategory);
        model.addAttribute("orderedProducts", orderedProducts);
        model.addAttribute("categories", categories);
        model.addAttribute("products", sortedProducts);
        return "productsByCategory";
    }
}
