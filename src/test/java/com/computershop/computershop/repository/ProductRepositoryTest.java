package com.computershop.computershop.repository;

import com.computershop.computershop.entity.Category;
import com.computershop.computershop.entity.Producer;
import com.computershop.computershop.entity.Product;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private Product product;
    private Category category;
    private Producer producer;
    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        category = Category.builder()
                .name("Laptopy")
                .build();
        categoryRepository.save(category);

        product = Product.builder()
                .name("Laptop Dell XPS 13")
                .description("Powerful and portable laptop with stunning display.")
                .price(new BigDecimal("1999.99"))
                .category(category)
                .producer(producer)
                .amountInMagazine(10)
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .build();
    }
}

