package com.computershop.repository;

import com.computershop.TestBase;
import com.computershop.entity.*;
import com.computershop.model.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
class OrderedProductRepositoryTest extends TestBase {
    @Autowired
    private OrderedProductRepository orderedProductRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProducerRepository producerRepository;
    @Autowired
    private UserRepository userRepository;
    private User user;
    private Category category;
    private Producer producer;
    private OrderedProduct orderedProduct;
    private List<OrderedProduct> orderedProducts;
    private Product product;
    private Order order;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .name("Laptopy")
                .build();
        categoryRepository.save(category);

        producer = Producer.builder()
                .name("Dell")
                .build();
        producerRepository.save(producer);

        user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .login("john_doe")
                .email("john@example.com")
                .address("123 Main Street")
                .role("user")
                .build();
        userRepository.save(user);


        product = Product.builder()
                .name("Laptop Dell XPS 13")
                .description("Powerful and portable laptop with stunning display.")
                .price(new BigDecimal("1999.99"))
                .category(category)
                .producer(producer)
                .amountInMagazine(10)
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .build();
        productRepository.save(product);

        order = Order.builder()
                .user(user)
                .orderedProducts(orderedProducts)
                .deliveryAddress("123 Main Street")
                .totalValue(BigDecimal.valueOf(100))
                .dateOfOrder(LocalDate.now())
                .status("pending")
                .build();
        orderRepository.save(order);

        orderedProduct = OrderedProduct.builder()
                .quantity(2)
                .product(product)
                .order(order)
                .price(BigDecimal.valueOf(50.99))
                .build();
    }
    @DisplayName("testing adding new OrderedProduct")
    @Test
    void shouldReturnSavedOrderedProduct() {
//        given
//        when
        OrderedProduct savedOrderedProduct = orderedProductRepository.save(orderedProduct);
//        then
        assertThat(savedOrderedProduct).isNotNull();
        assertThat(savedOrderedProduct.getId()).isEqualTo(orderedProduct.getId());
    }
    @DisplayName("testing deleting OrderedProduct by id")
    @Test
    void shouldDeleteOrderedProductById() {
//        given
        orderedProductRepository.save(orderedProduct);
//        when
        orderedProductRepository.deleteById(orderedProduct.getId());
        Optional<OrderedProduct> foundOrderedProduct = orderedProductRepository.findById(orderedProduct.getId());
//        then
        assertThat(foundOrderedProduct).isEmpty();
    }
    @DisplayName("testing finding all OrderedProducts")
    @Test
    void shouldReturnListOfAllOrderedProducts() {
//        given
        orderedProductRepository.save(orderedProduct);
//        when
        List<OrderedProduct> orderedProducts = orderedProductRepository.findAll();
//        then
        assertThat(orderedProducts).isNotEmpty();
        assertThat(orderedProducts.size()).isEqualTo(1);
        assertThat(orderedProducts.get(0).getId()).isEqualTo(orderedProduct.getId());
    }
    @DisplayName("testing finding OrderedProduct by id")
    @Test
    void shouldReturnOrderedProductById() {
//        given
        orderedProductRepository.save(orderedProduct);
//        when
        Optional<OrderedProduct> foundOrderedProduct = orderedProductRepository.findById(orderedProduct.getId());
//        then
        assertThat(foundOrderedProduct).isNotNull();
        assertThat(foundOrderedProduct.get().getId()).isEqualTo(orderedProduct.getId());
    }
    @DisplayName("testing finding orderedProduct by id when does not exist")
    @Test
    void shouldReturnEmptyOptional() {
//        given
//        when
        Optional<OrderedProduct> foundOrderedProduct = orderedProductRepository.findById(orderedProduct.getId());
//        then
        assertThat(foundOrderedProduct).isEmpty();
    }
}

















