package com.computershop.computershop.service;

import com.computershop.computershop.entity.Category;
import com.computershop.computershop.entity.Product;
import com.computershop.computershop.entity.dto.CategoryDto;
import com.computershop.computershop.repository.CategoryRepository;
import com.computershop.computershop.service.impl.CategoryServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
class CategoryServiceTest {
    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private final CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepository);
    private Category category;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
        category = Category.builder()
                .name("Laptopy")
                .build();
        categoryDto = CategoryDto.mapToDto(category);
    }

    @DisplayName("testing adding new category")
    @Test
    void shouldReturnAddedCategory() {
//        given
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
//        when
        Category savedCategory = categoryService.addCategory(categoryDto);
//        then
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isEqualTo(category.getId());
    }

    @DisplayName("testing adding new category when there is a category with the same name")
    @Test
    void shouldThrowExceptionWhenTheSameName() {
//        given
        Category category2 = Category.builder()
                .name("Laptopy")
                .build();
        CategoryDto categoryDto2 = CategoryDto.mapToDto(category2);
        when(categoryRepository.save(any(Category.class))).thenThrow(DataIntegrityViolationException.class);
//        when
//        then
        assertThrows(DataIntegrityViolationException.class, () -> categoryService.addCategory(categoryDto2));
    }

    @DisplayName("testing deleting category by id")
    @Test
    void shouldDeleteCategoryById() {
//        given
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
//        when
        categoryService.deleteCategoryById(category.getId());
//        then
        verify(categoryRepository).deleteById(category.getId());
    }

    @DisplayName("testing editing category by id")
    @Test
    void shouldReturnEditedCategory() {
//        given
        Category categoryToEdit = Category.builder()
                .name("Procesory")
                .build();
        CategoryDto categoryToEditDto = CategoryDto.mapToDto(categoryToEdit);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
//        when

        Category editedCategoryById = categoryService.editCategoryById(category.getId(), categoryToEditDto);
//        then
        assertThat(editedCategoryById).isNotNull();
        assertThat(editedCategoryById.getId()).isEqualTo(category.getId());
    }

    @DisplayName("testing editing category by id when there is no category with that id")
    @Test
    void shouldThrowExceptionWhenThereIsNoCategoryById() {
//        given
        Category categoryToEdit = Category.builder()
                .name("Procesory")
                .build();
        CategoryDto categoryToEditDto = CategoryDto.mapToDto(categoryToEdit);
        when(categoryRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);
//        when

//        then
        assertThrows(EntityNotFoundException.class, () -> categoryService.editCategoryById(category.getId(), categoryToEditDto));
    }
    @DisplayName("testing editing category when name is the same like already exist")
    @Test
    void shouldThrowExceptionWhenNameISTheSame() {
//        given
        Category categoryToEdit = Category.builder()
                .name("Procesory")
                .build();
        CategoryDto categoryToEditDto = CategoryDto.mapToDto(categoryToEdit);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenThrow(DataIntegrityViolationException.class);
//        when
//        then
        assertThrows(DataIntegrityViolationException.class, () -> categoryService.editCategoryById(category.getId(), categoryToEditDto));
    }
    @DisplayName("testingEditingCategoryWhenNameIsEmpty")
    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
//        given
        Category categoryToEdit = Category.builder()
                .name("")
                .build();
        CategoryDto categoryToEditDto = CategoryDto.mapToDto(categoryToEdit);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenThrow(IllegalArgumentException.class);
//        when
//        then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> categoryService.editCategoryById(category.getId(), categoryToEditDto));
        assertEquals("name can't be empty!", exception.getMessage());
    }
    @DisplayName("testing finding list of all categories")
    @Test
    void shouldReturnListOfAllCategories() {
//        given
        when(categoryRepository.findAll()).thenReturn(List.of(category));
//        when
        List<CategoryDto> categories = categoryService.findAllCategories();
//        then
        assertThat(categories).isNotEmpty();
        assertThat(categories.size()).isEqualTo(1);
        assertThat(categories.get(0).getId()).isEqualTo(category.getId());
    }
//    @DisplayName("testing finding products for category")
//    @Test
//    void shouldReturnListOfProductForCategory() {
////        given
////        dodać nowy produkt
//        when(categoryRepository.save(any(Category.class))).thenReturn(category);
//        when(categoryRepository.findAll()).thenReturn(List.of(category));
////        when
//        List<ProductDto> products = categoryService.findAllProductForCategory(category);
////        then
//        assertThat(products).isNotEmpty();
//        assertThat(products.size()).isEqualTo(1);
//    }
}



































