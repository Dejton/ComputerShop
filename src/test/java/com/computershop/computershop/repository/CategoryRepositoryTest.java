package com.computershop.computershop.repository;

import com.computershop.computershop.TestBase;
import com.computershop.computershop.entity.Category;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryRepositoryTest extends TestBase {

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
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @AfterEach
//    public void resetIdSequence() {
//        jdbcTemplate.execute("ALTER TABLE categories ALTER COLUMN id RESTART WITH 1");
//    }


    @DisplayName("testing adding new category")
    @Test
    void shouldReturnAddedCategory() {
//        given
//        when
        Category savedCategory = categoryRepository.save(category);
//        then
        var actualCategory = categoryRepository.findById(savedCategory.getId());
        assertThat(actualCategory).contains(savedCategory);
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



































