package com.computershop.computershop.service;

import com.computershop.computershop.entity.*;
import com.computershop.computershop.entity.dto.OrderedProductDto;
import com.computershop.computershop.repository.OrderedProductRepository;
import com.computershop.computershop.service.impl.OrderedProductServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class OrderedProductServiceTest {
    private final OrderedProductRepository orderedProductRepository = mock(OrderedProductRepository.class);
    private final OrderedProductServiceImpl orderedProductService = new OrderedProductServiceImpl(orderedProductRepository);

    private OrderedProduct orderedProduct;
    private Product product;
    private Order order;
    private OrderedProductDto orderedProductDto;

    @BeforeEach
    void setUp() {
        orderedProductRepository.deleteAll();

        orderedProduct = OrderedProduct.builder()
                .quantity(2)
                .product(product)
                .order(order)
                .price(BigDecimal.valueOf(50.99))
                .build();
        orderedProductDto = OrderedProductDto.matToDto(orderedProduct);
    }
    @DisplayName("testing adding new OrderedProduct")
    @Test
    void shouldReturnSavedOrderedProduct() {
//        given
        when(orderedProductRepository.save(orderedProduct)).thenReturn(orderedProduct);
//        when
        OrderedProduct savedOrderedProduct = orderedProductService.addOrderedProduct(orderedProductDto);
//        then
        assertThat(savedOrderedProduct).isNotNull();
        assertThat(savedOrderedProduct.getId()).isEqualTo(orderedProduct.getId());
    }
    @DisplayName("testing deleting ordered product by id")
    @Test
    void shouldDeleteOrderedProductById() {
//        given
        when(orderedProductRepository.save(orderedProduct)).thenReturn(orderedProduct);
//        when
        orderedProductService.deleteOrderedProductById(orderedProduct.getId());
//        then
        verify(orderedProductRepository).deleteById(orderedProduct.getId());
    }
    @DisplayName("testing finding all ordered products")
    @Test
    void shouldReturnListOfAllOrderedProducts() {
//        given
        when(orderedProductRepository.findAll()).thenReturn(List.of(orderedProduct));
//        when
        List<OrderedProductDto> orderedProducts = orderedProductService.getAllOrderedProducts();
//        then
        assertThat(orderedProducts).isNotEmpty();
        assertThat(orderedProducts.size()).isEqualTo(1);
        assertThat(orderedProducts.get(0).getId()).isEqualTo(orderedProduct.getId());
    }
    @DisplayName("testing fiding ordered product by id")
    @Test
    void shouldReturnOrderedProductById() {
//        given
        when(orderedProductRepository.findById(anyLong())).thenReturn(Optional.of(orderedProduct));
//        when
        OrderedProductDto foundOrderedProduct = orderedProductService.getOrderedProductById(orderedProduct.getId());
//        then
        assertThat(foundOrderedProduct).isNotNull();
        assertThat(foundOrderedProduct.getId()).isEqualTo(orderedProduct.getId());
    }
    @DisplayName("testing finding ordered product by id when does not exist")
    @Test
    void shouldThrowExceptionWhenNotExist() {
//        given
        when(orderedProductRepository.findById(anyLong())).thenReturn(Optional.empty());
//        when
//        then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> orderedProductService.getOrderedProductById(orderedProduct.getId()));
        assertEquals("Ordered product with id: " + orderedProduct.getId() + " does not exist!", exception.getMessage());

    }
    @DisplayName("testing finding ordered product by id when argument is illegal")
    @Test
    void shouldThrowExceptionWhenArgumentIsIllegalById() {
//        given
        when(orderedProductRepository.findById(anyLong())).thenReturn(Optional.of(orderedProduct));
//        when
//        then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderedProductService.getOrderedProductById(-10));
        assertEquals("Enter correct value!", exception.getMessage());
    }
}






















