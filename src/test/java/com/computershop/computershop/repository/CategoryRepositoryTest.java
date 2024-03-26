package com.computershop.computershop.repository;

import com.computershop.computershop.entity.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    Category category;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
        category = Category.builder()
                .name("Laptopy")
                .build();
    }


    @DisplayName("testing adding new category")
    @Test
    void shouldReturnAddedCategory() {
//        given
//        when
        Category savedCategory = categoryRepository.save(category);
//        then
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isEqualTo(category.getId());
        assertThat(savedCategory).isEqualTo(category);
    }

    @DisplayName("testing adding category with the same name")
    @Test
    void shouldThrowExceptionWhenAddingCategoryWithTheSameName() {
//        given
        categoryRepository.save(category);
        Category category2 = Category.builder()
                .name("Laptopy")
                .build();
//        when
//        then
        assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(category2));
    }

    @DisplayName("testing deleting existing category by id")
    @Test
    void shouldDeleteExistingCategoryById() {
//        given
        categoryRepository.save(category);
//        when
        categoryRepository.deleteById(category.getId());
        Optional<Category> foundCategory = categoryRepository.findById(category.getId());
//        then
        assertThat(foundCategory).isEmpty();
    }
//    ------------------------------------------------------------------------------------------------------------------
//                                       TRZEBA POPRAWIĆ

//    @DisplayName("testing deleting category that not exist")
//    @Test
//    void shouldThrowExceptionWhenThereIsNoCategoryWithIdToDelete() {
////        given
////        when
////        then
//        assertThrows(EmptyResultDataAccessException.class, () -> categoryRepository.deleteById(category.getId()));
//    }
    @DisplayName("testing finding for all categories")
    @Test
    void shouldReturnListOfAllCategories() {
//        given
        categoryRepository.save(category);
//        when
        List<Category> categories = categoryRepository.findAll();
//        then
        assertThat(categories).isNotEmpty();
        assertThat(categories.size()).isEqualTo(1);
        assertThat(categories.get(0).getId()).isEqualTo(category.getId());
    }
    @DisplayName("testing finding for all categories when there is no categories")
    @Test
    void shouldReturnEmptyListWhenThereIsNoCategories() {
//        given
//        when
        List<Category> categories = categoryRepository.findAll();
//        then
        assertThat(categories).isEmpty();
    }


}



































