package com.computershop.computershop.controller.rest;

import com.computershop.computershop.entity.Category;
import com.computershop.computershop.entity.dto.CategoryDto;
import com.computershop.computershop.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category addCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }
    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }
    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable long id) {
        categoryService.deleteCategoryById(id);
    }
}
