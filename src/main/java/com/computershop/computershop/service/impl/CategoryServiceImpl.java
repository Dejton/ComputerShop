package com.computershop.computershop.service.impl;

import com.computershop.computershop.entity.Category;
import com.computershop.computershop.entity.Product;
import com.computershop.computershop.entity.dto.CategoryDto;
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
    public Category addCategory(CategoryDto categoryDto ) {
        Category category = CategoryDto.mapFromDto(categoryDto);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category editCategoryById(long id, CategoryDto updatedCategoryDto) {
        Category categoryToUpdate = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));
        Category updatedCategory = CategoryDto.mapFromDto(updatedCategoryDto);
        categoryToUpdate.setName(updatedCategory.getName());
        if (updatedCategory.getName().isBlank()) {
            throw new IllegalArgumentException("name can't be empty!");
        }
        return categoryRepository.save(categoryToUpdate);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
       List<Category> categories = categoryRepository.findAll();
      return categories.stream()
               .map(CategoryDto::mapToDto)
               .toList();

    }

}
