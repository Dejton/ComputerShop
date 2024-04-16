package com.computershop.controller.web;

import com.computershop.model.dto.CategoryDto;
import com.computershop.model.dto.OrderedProductDto;
import com.computershop.model.dto.ProductDto;
import com.computershop.service.CategoryService;
import com.computershop.service.OrderService;
import com.computershop.service.ProductService;
import com.computershop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Optional.ofNullable;

@Controller
public class HomePageController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;
    @Autowired
    private HttpServletRequest request;


    public HomePageController(CategoryService categoryService, ProductService productService, OrderService orderService, UserService userService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        return showProducts(model, principal,null, request.getRequestURI());
    }
    @GetMapping("/products/{category}")
    public String index(@PathVariable String category, Model model, Principal principal) {
        return showProducts(model, principal,category, request.getRequestURI());
    }

    @PostMapping("/add-to-cart")
    public String addProductToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        orderService.addProductToOrder(userId, productId, quantity);
        return "redirect:/";
    }
    private String showProducts(Model model, Principal principal, String category, String currentPath) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        List<ProductDto> products = productService.getAllProducts();
        List<OrderedProductDto> orderedProducts = ofNullable(principal)
                .map(Principal::getName)
                .map(userService::getUserByLogin)
                .map(user -> orderService.getAllProductsInCart(user, "In progress"))
                .orElseGet(Collections::emptyList);
        List<ProductDto> productsByCategory = products.stream()
                .filter(productDto -> productDto.getCategory().getName().equals(category))
                .toList();
        BigDecimal totalAmount = orderedProducts.stream()
                .map(OrderedProductDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        List<ProductDto> sortedProducts = products.stream()
                .sorted(Comparator.comparingLong(ProductDto::getId).reversed())
                .toList();

        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("orderedProducts", orderedProducts);
        model.addAttribute("productsByCategory", productsByCategory);
        model.addAttribute("categories", categories);
        model.addAttribute("products", sortedProducts);
        model.addAttribute("category", category);
        model.addAttribute("currentPath", currentPath);
        return "index";
    }
}
