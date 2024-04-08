package com.computershop.computershop.controller.web;

import com.computershop.computershop.entity.Order;
import com.computershop.computershop.entity.OrderedProduct;
import com.computershop.computershop.entity.dto.CategoryDto;
import com.computershop.computershop.entity.dto.OrderDto;
import com.computershop.computershop.entity.dto.OrderedProductDto;
import com.computershop.computershop.entity.dto.ProductDto;
import com.computershop.computershop.service.CategoryService;
import com.computershop.computershop.service.OrderService;
import com.computershop.computershop.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class HomePageController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final OrderService orderService;


    public HomePageController(CategoryService categoryService, ProductService productService, OrderService orderService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        List<ProductDto> products = productService.getAllProducts();

        List<ProductDto> sortedProducts = products.stream()
                .sorted(Comparator.comparingLong(ProductDto::getId).reversed())
                .toList();
//        Long orderId = (Long) session.getAttribute("orderId");
//        Long orderId = 1L;
//        BigDecimal totalValue = orderService.getOrderById(orderId).getTotalValue();
//
//        // Pobieramy informacje o zamówieniu na podstawie identyfikatora
//        List<OrderedProductDto> orderedProductsDto = Collections.emptyList();
//        if (orderId != null) {
//            List<OrderedProduct> orderedProducts = orderService.getOrderById(orderId).getOrderedProducts();
//            orderedProductsDto = orderedProducts.stream()
//                    .map(OrderedProductDto::matToDto)
//                    .toList();
//        }


        model.addAttribute("categories", categories);
        model.addAttribute("products", sortedProducts);
//        model.addAttribute("orderedProducts", orderedProductsDto);
//        model.addAttribute("totalValue", totalValue);
        return "index";
    }
}
