package com.computershop.controller.web;

import com.computershop.model.dto.CategoryDto;
import com.computershop.service.CategoryService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/add-category")
public class AddCategoryController {
    private final CategoryService categoryService;

    public AddCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping
    public String showAddCategoryForm(Model model) {
        List<CategoryDto> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return"addCategory";
    }
    @PostMapping
    @Secured("ROLE_ADMIN")
    public String addCategory(@ModelAttribute CategoryDto categoryDto, Model model) {
        categoryService.addCategory(categoryDto);
        return "redirect:/";
    }
}
