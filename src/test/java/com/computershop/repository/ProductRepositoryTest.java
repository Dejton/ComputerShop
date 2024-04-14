package com.computershop.repository;

import com.computershop.TestBase;
import com.computershop.model.entity.Category;
import com.computershop.model.entity.Producer;
import com.computershop.model.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest extends TestBase {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProducerRepository producerRepository;
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
        producer = Producer.builder()
                .name("Dell")
                .build();
        producerRepository.save(producer);

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

    @DisplayName("testing adding new product")
    @Test
    void shouldReturnSavedProduct() {
//        given
//        when
        Product savedProduct = productRepository.save(product);
//        then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isEqualTo(product.getId());
    }
    @DisplayName("testing adding product with the same name")
    @Test
    void shouldThrowExceptionWhenNameIsTheSame() {
//        given
        Product product2 = Product.builder()
                .name("Laptop Dell XPS 13")
                .description("Powerful and portable laptop with stunning display.")
                .price(new BigDecimal("1999.99"))
                .category(category)
                .producer(producer)
                .amountInMagazine(10)
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .build();
        productRepository.save(product);
//        when
//        then
        assertThrows(DataIntegrityViolationException.class, () -> productRepository.save(product2));
    }
    @DisplayName("testing deleting product by id")
    @Test
    void shouldDeleteProductById() {
//        given
        productRepository.save(product);
//        when
        productRepository.deleteById(product.getId());
        Optional<Product> byId = productRepository.findById(product.getId());
//        then
        assertThat(byId).isEmpty();
    }
//    ------------------------------------------------
//    @DisplayName("testing deleting product when there is no product by id")
//    @Test
//    void shouldThrowExceptionWhenThereIsNoId() {
////        given
////        when
//
////        then
//
//    }
    @DisplayName("testing finding all products")
    @Test
    void shouldReturnListOfAllProducts() {
//        given
        productRepository.save(product);
//        when
        List<Product> products = productRepository.findAll();
//        then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.get(0).getId()).isEqualTo(product.getId());
    }
    @DisplayName("testing finding all products when there are no products")
    @Test
    void shouldReturnEmptyListOfProducts() {
//        given
//        when
        List<Product> products = productRepository.findAll();
//        then
        assertThat(products).isEmpty();
    }
    @DisplayName("testing finding product by id")
    @Test
    void shouldReturnProductById() {
//        given
        productRepository.save(product);
//        when
        Optional<Product> foundProduct = productRepository.findById(product.getId());
//        then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.get().getId()).isEqualTo(product.getId());
    }
    @DisplayName("testing finding product by name")
    @Test
    void shouldReturnProductByTheName() {
//        given
        productRepository.save(product);
//        when
        Optional<Product> foundProduct = productRepository.findByName(product.getName());
//        then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.get().getId()).isEqualTo(product.getId());
    }
    @DisplayName("testing finding products by price")
    @Test
    void shouldReturnListOfProductsByPrice() {
//        given
        productRepository.save(product);
//        when
        List<Product> products = productRepository.findByPrice(product.getPrice());
//        then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.get(0).getId()).isEqualTo(product.getId());
    }
    @DisplayName("testing finding products by price when there is no products for that price")
    @Test
    void shouldReturnEmptyListOfProductsByPrice() {
//        given
//        when
        List<Product> products = productRepository.findByPrice(product.getPrice());
//        then
        assertThat(products).isEmpty();
    }
    @DisplayName("testing finding product for price range")
    @Test
    void shouldReturnListOfProductByPriceRange() {
//        given
        Product product2 = Product.builder()
                .name("inna nazwa")
                .description("Powerful and portable laptop with stunning display.")
                .price(new BigDecimal("10.99"))
                .category(category)
                .producer(producer)
                .amountInMagazine(10)
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .build();
        productRepository.save(product);
        productRepository.save(product2);
//        when
        List<Product> products = productRepository.findByPriceRange(new BigDecimal("5.00"), new BigDecimal("2000.00"));
//        then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(2);
        assertThat(products.get(0).getId()).isEqualTo(product.getId());
    }
    @DisplayName("testing finding product by price range when there is no product in that price range")
    @Test
    void shouldReturnEmptyListOfProductsByPriceRange() {
//        given
//        when
        List<Product> products = productRepository.findByPriceRange(new BigDecimal("500.00"), new BigDecimal("2000.00"));
//        then
        assertThat(products).isEmpty();
    }
    @DisplayName("testing finding products for category")
    @Test
    void shouldReturnListOfProductForCategory() {
//        given
        productRepository.save(product);
//        when
        List<Product> products = productRepository.findByCategory(product.getCategory());
//        then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.get(0).getId()).isEqualTo(product.getId());
    }
    @DisplayName("testing finding products by category when the are no product for that category")
    @Test
    void shouldReturnEmptyListOfProductByCategory() {
//        given
//        when
        List<Product> products = productRepository.findByCategory(product.getCategory());
//        then
        assertThat(products).isEmpty();
    }
    @DisplayName("testing finding products for producer")
    @Test
    void shouldReturnListOfProductForProducer() {
//        given
        productRepository.save(product);
//        when
        List<Product> products = productRepository.findByProducer(product.getProducer());
//        then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.get(0).getId()).isEqualTo(product.getId());
    }
    @DisplayName("testing finding products by producer when the are no product for that producer")
    @Test
    void shouldReturnEmptyListOfProductByProducer() {
//        given
//        when
        List<Product> products = productRepository.findByProducer(product.getProducer());
//        then
        assertThat(products).isEmpty();
    }
}



































