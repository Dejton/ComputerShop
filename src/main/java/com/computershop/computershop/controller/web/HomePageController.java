package com.computershop.computershop.controller.web;

import com.computershop.computershop.entity.dto.CategoryDto;
import com.computershop.computershop.entity.dto.ProductDto;
import com.computershop.computershop.service.CategoryService;
import com.computershop.computershop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomePageController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public HomePageController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "index";
    }
}
