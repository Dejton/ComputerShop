package com.computershop.computershop.service;

import com.computershop.computershop.entity.Category;
import com.computershop.computershop.entity.Product;

import java.util.List;

public interface CategoryService {
    Category addCategory(Category category);
    void deleteCategoryById(long id);
    Category editCategoryById(long id, Category categoryToUpdate);
    List<Category> findAllCategories();

    List<Product> findAllProductForCategory(Category category);
}
