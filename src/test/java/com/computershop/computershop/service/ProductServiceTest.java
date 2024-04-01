package com.computershop.computershop.service;

import com.computershop.computershop.entity.Category;
import com.computershop.computershop.entity.Producer;
import com.computershop.computershop.entity.Product;
import com.computershop.computershop.entity.dto.ProductDto;
import com.computershop.computershop.repository.ProductRepository;
import com.computershop.computershop.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
class ProductServiceTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductServiceImpl productService = new ProductServiceImpl(productRepository);
    private Product product;
    private ProductDto productDto;
    private Producer producer;
    private Category category;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();

        producer = Producer.builder()
                .name("Dell")
                .build();

        category = Category.builder()
                .name("Laptopy")
                .build();

        product = Product.builder()
                .name("Laptop Dell XPS 13")
                .description("Powerful and portable laptop with stunning display.")
                .price(new BigDecimal("1999.99"))
                .category(category)
                .producer(producer)
                .amountInMagazine(10)
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .build();
        productDto = ProductDto.matToDto(product);
    }

    @DisplayName("testing adding new Product")
    @Test
    void shouldReturnSavedProduct() {
//        given
        when(productRepository.save(any(Product.class))).thenReturn(product);
//        when
        Product savedProduct = productService.addProduct(productDto);
//        then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isEqualTo(product.getId());
    }

}



































