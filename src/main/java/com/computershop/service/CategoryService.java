package com.computershop.service;

import com.computershop.model.entity.Category;
import com.computershop.model.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    Category addCategory(CategoryDto categoryDto);
    void deleteCategoryById(long id);
    Category editCategoryById(long id, CategoryDto categoryToUpdateDto);
    List<CategoryDto> getAllCategories();

}
