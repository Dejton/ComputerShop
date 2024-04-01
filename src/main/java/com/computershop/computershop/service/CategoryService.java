package com.computershop.computershop.service;

import com.computershop.computershop.entity.Category;
import com.computershop.computershop.entity.Product;
import com.computershop.computershop.entity.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    Category addCategory(CategoryDto categoryDto);
    void deleteCategoryById(long id);
    Category editCategoryById(long id, CategoryDto categoryToUpdateDto);
    List<CategoryDto> findAllCategories();

}
