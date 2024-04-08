package com.computershop.computershop.controller.web;

import com.computershop.computershop.entity.dto.CategoryDto;
import com.computershop.computershop.entity.dto.ProducerDto;
import com.computershop.computershop.entity.dto.ProductDto;
import com.computershop.computershop.service.CategoryService;
import com.computershop.computershop.service.ProducerService;
import com.computershop.computershop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class addProductController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ProducerService producerService;

    public addProductController(CategoryService categoryService, ProductService productService, ProducerService producerService) {
        this.categoryService = categoryService;
        this.productService = productService;

        this.producerService = producerService;
    }

    @GetMapping("/add-product")
    public String showAddProductForm(Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        List<ProducerDto> producers = producerService.getAllProducers();
        model.addAttribute("categories", categories);
        model.addAttribute("producers", producers);
        return "addProduct";
    }
    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute ProductDto productDto, Model model) {
        productService.addProduct(productDto);
        return "redirect:/";
    }
}
