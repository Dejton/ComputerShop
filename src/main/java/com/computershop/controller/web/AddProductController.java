package com.computershop.controller.web;

import com.computershop.model.dto.CategoryDto;
import com.computershop.model.dto.ProducerDto;
import com.computershop.model.dto.ProductDto;
import com.computershop.service.CategoryService;
import com.computershop.service.ProducerService;
import com.computershop.service.ProductService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/add-product")
public class AddProductController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ProducerService producerService;

    public AddProductController(CategoryService categoryService, ProductService productService, ProducerService producerService) {
        this.categoryService = categoryService;
        this.productService = productService;

        this.producerService = producerService;
    }

    @GetMapping
    public String showAddProductForm(Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        List<ProducerDto> producers = producerService.getAllProducers();
        model.addAttribute("categories", categories);
        model.addAttribute("producers", producers);
        return "addProduct";
    }
    @PostMapping
    @Secured("ADMIN_ROLE")
    public String addProduct(@ModelAttribute ProductDto productDto, Model model) {
        productService.addProduct(productDto);
        return "redirect:/";
    }
}
