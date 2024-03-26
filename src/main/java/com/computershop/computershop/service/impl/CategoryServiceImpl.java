package com.computershop.computershop.service.impl;

import com.computershop.computershop.entity.Category;
import com.computershop.computershop.entity.Product;
import com.computershop.computershop.repository.CategoryRepository;
import com.computershop.computershop.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category addCategory(Category category) {
       return categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category editCategoryById(long id, Category updatedCategory) {
        Category categoryToUpdate = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));
        categoryToUpdate.setName(updatedCategory.getName());
        if (updatedCategory.getName().isEmpty()) {
            throw new IllegalArgumentException("name can't be empty!");
        }
        return categoryRepository.save(categoryToUpdate);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
//--------------------------------Dorobić jak skończę repo dla produktu
    @Override
    public List<Product> findAllProductForCategory(Category category) {
        return null;
    }
}
