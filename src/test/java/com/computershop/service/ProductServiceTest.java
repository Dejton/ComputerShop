package com.computershop.service;

import com.computershop.model.entity.Category;
import com.computershop.model.entity.Producer;
import com.computershop.model.entity.Product;
import com.computershop.model.dto.ProductDto;
import com.computershop.repository.ProductRepository;
import com.computershop.service.impl.ProductServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
                .id(1)
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

    @DisplayName("testing adding product with the same name")
    @Test
    void shouldThrowExceptionWhenTheSameName() {
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
        ProductDto product2Dto = ProductDto.matToDto(product2);
        when(productRepository.save(product)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenThrow(DataIntegrityViolationException.class);
//        when
//        then
        assertThrows(DataIntegrityViolationException.class, () -> productService.addProduct(product2Dto));
    }

    @DisplayName("testing deleting existing product")
    @Test
    void shouldDeleteExistingProduct() {
//        given
        when(productRepository.save(any(Product.class))).thenReturn(product);
//        when
        productService.deleteProductById(product.getId());
//        then
        verify(productRepository).deleteById(product.getId());
    }

    @DisplayName("testing editing product by id")
    @Test
    void shouldReturnEditedProductById() {
//        given
        Product updatedProduct = Product.builder()
                .name("Laptop HP pavilion")
                .description("Powerful and portable laptop with stunning display.")
                .price(new BigDecimal("1999.99"))
                .category(category)
                .producer(producer)
                .amountInMagazine(10)
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .build();
        ProductDto updatedProductDto = ProductDto.matToDto(updatedProduct);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
//        when
        Product editedProduct = productService.editProductById(product.getId(), updatedProductDto);
//        then
        assertThat(editedProduct).isNotNull();
        assertThat(editedProduct.getId()).isEqualTo(product.getId());
        assertThat(editedProduct.getName()).isEqualTo(product.getName());
        assertThat(editedProduct.getDescription()).isEqualTo(product.getDescription());
        assertThat(editedProduct.getPrice()).isEqualTo(product.getPrice());
        assertThat(editedProduct.getCategory()).isEqualTo(product.getCategory());
        assertThat(editedProduct.getProducer()).isEqualTo(product.getProducer());
        assertThat(editedProduct.getAmountInMagazine()).isEqualTo(product.getAmountInMagazine());
        assertThat(editedProduct.getImages()).isEqualTo(product.getImages());
    }

    @DisplayName("testing editing product by id when there is the same name")
    @Test
    void shouldThrowExceptionWhenEditedNameIsTheSame() {
//        given
        Product updatedProduct = Product.builder()
                .name("Laptop Dell XPS 13")
                .description("Powerful and portable laptop with stunning display.")
                .price(new BigDecimal("1999.99"))
                .category(category)
                .producer(producer)
                .amountInMagazine(10)
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .build();
        ProductDto updatedProductDto = ProductDto.matToDto(updatedProduct);
        when(productRepository.save(any(Product.class))).thenThrow(DataIntegrityViolationException.class);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
//        when
//        then
        assertThrows(DataIntegrityViolationException.class, () -> productService.editProductById(product.getId(), updatedProductDto));
    }

    @DisplayName("testing editing product by id when the name is empty")
    @Test
    void shouldThrowExceptionWhenTheNameIsEmpty() {
//        given
        Product updatedProduct = Product.builder()
                .name("  ")
                .description("Powerful and portable laptop with stunning display.")
                .price(new BigDecimal("1999.99"))
                .category(category)
                .producer(producer)
                .amountInMagazine(10)
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .build();
        ProductDto updatedProductDto = ProductDto.matToDto(updatedProduct);
        when(productRepository.save(any(Product.class))).thenThrow(IllegalArgumentException.class);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
//        when
//        then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.editProductById(category.getId(), updatedProductDto));
        assertEquals("name cannot be empty!", exception.getMessage());
    }

    @DisplayName("testing editing product by id when the product don't exist")
    @Test
    void shouldThrowExceptionWhenProductNotExist() {
//        given
        Product updatedProduct = Product.builder()
                .name("Laptopy")
                .description("Powerful and portable laptop with stunning display.")
                .price(new BigDecimal("1999.99"))
                .category(category)
                .producer(producer)
                .amountInMagazine(10)
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .build();
        ProductDto updatedProductDto = ProductDto.matToDto(updatedProduct);
        when(productRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);
//        when
//        then
        assertThrows(EntityNotFoundException.class, () -> productService.editProductById(product.getId(), updatedProductDto));
    }

    @DisplayName("testing finding all products")
    @Test
    void shouldReturnListOfAllProducts() {
//        given
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findAll()).thenReturn(List.of(product));
//        when
        List<ProductDto> products = productService.getAllProducts();
//        then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.getFirst().getId()).isEqualTo(product.getId());
    }

    @DisplayName("testing finding product by name")
    @Test
    void shouldReturnProductByName() {
//        given
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findByName(anyString())).thenReturn(Optional.of(product));
//        when
        ProductDto foundProduct = productService.getProductByName(product.getName());
//        then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(product.getId());
    }

    @DisplayName("testing finding product by name when no exist")
    @Test
    void shouldThrowExceptionWhenProductByNameNotExist() {
//        given
        when(productRepository.findByName(anyString())).thenReturn(Optional.empty());
//        when
//        then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> productService.getProductByName(product.getName()));
        assertEquals("product with this name does not exist!", exception.getMessage());
    }

    @DisplayName("testing finding product by name when argument is illegal")
    @Test
    void shouldThrowExceptionWhenArgumentIsIllegalByName() {
//        given
        when(productRepository.save(any(Product.class))).thenReturn(product);
//        when
//        then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.getProductByName(" "));
        assertEquals("Invalid name value!", exception.getMessage());

    }

    @DisplayName("testing finding product by id")
    @Test
    void shouldReturnProductById() {
//        given
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
//        when
        ProductDto foundProduct = productService.getProductById(product.getId());
//        then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(product.getId());
    }

    @DisplayName("testing finding product by id when no exist")
    @Test
    void shouldThrowExceptionWhenProductByIdNotExist() {
//        given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
//        when
//        then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> productService.getProductById(product.getId()));
        assertEquals("product with id: " + product.getId() + "not found.", exception.getMessage());
    }

    @DisplayName("testing finding product by id when argument is illegal")
    @Test
    void shouldThrowExceptionWhenArgumentIsIllegalById() {
//        given
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
//        when
//        then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.getProductById(-8));
        assertEquals("Enter correct value!", exception.getMessage());
    }

    @DisplayName("testing finding product by price")
    @Test
    void shouldReturnProductByPrice() {
//        given
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findByPrice(product.getPrice())).thenReturn(List.of(product));
//        when
        List<ProductDto> products = productService.getProductsByPrice(product.getPrice());
//        then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.getFirst().getId()).isEqualTo(product.getId());
    }

    @DisplayName("testing finding products by price when products does not exists")
    @Test
    void shouldReturnEmptyListOfProductsWhenProductsByPriceDoesNotExists() {
//        given
        when(productRepository.findByPrice(product.getPrice())).thenReturn(Collections.emptyList());
//        when
        List<ProductDto> products = productService.getProductsByPrice(product.getPrice());
//        then
        assertThat(products).isEmpty();
    }

    @DisplayName("testing finding products when argument is illegal")
    @Test
    void shouldThrowExceptionWhenArgumentIsIllegalByPrice() {
//        given
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findByPrice(null)).thenThrow(IllegalArgumentException.class);
//        when
//        then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.getProductsByPrice(null));
        assertEquals("Enter correct value!", exception.getMessage());
    }

    @DisplayName("testing finding products by category")
    @Test
    void shouldReturnListOfProductsByCategory() {
//        given
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findByCategory(product.getCategory())).thenReturn(List.of(product));
//        when
        List<ProductDto> products = productService.getProductsByCategory(product.getCategory());
//        then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.getFirst().getId()).isEqualTo(product.getId());
    }

    @DisplayName("testing finding products by category when products does not exist")
    @Test
    void shouldReturnEmptyListOfProductsByCategory() {
//        given
        when(productRepository.findByCategory(any(Category.class))).thenReturn(Collections.emptyList());
//        when
        List<ProductDto> products = productService.getProductsByCategory(product.getCategory());
//        then
        assertThat(products).isEmpty();
    }

    @DisplayName("testing finding products by category when argument is illegal")
    @Test
    void shouldThrowExceptionWhenArgumentIsIllegalByCategory() {
//        given
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findByCategory(null)).thenThrow(IllegalArgumentException.class);
//        when
//        then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.getProductsByCategory(null));
        assertEquals("Enter correct value!", exception.getMessage());
    }

    @DisplayName("testing finding products by producer")
    @Test
    void shouldReturnListOfProductsByProducer() {
//        given
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findByProducer(any(Producer.class))).thenReturn(List.of(product));
//        when
        List<ProductDto> products = productService.getProductsByProducer(producer);
//        then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.getFirst().getId()).isEqualTo(product.getId());
    }
    @DisplayName("testing finding products by producer when products does not exists")
    @Test
    void shouldReturnEmptyListOfProductsByProducer() {
//        given
        when(productRepository.findByProducer(any(Producer.class))).thenReturn(Collections.emptyList());
//        when
        List<ProductDto> products = productService.getProductsByProducer(product.getProducer());
//        then
        assertThat(products).isEmpty();
    }
    @DisplayName("testing finding products by producer when argument is illegal")
    @Test
    void shouldThrowExceptionWhenArgumentIsIllegalByProducer() {
//        given
        when(productRepository.findByProducer(null)).thenThrow(IllegalArgumentException.class);
//        when
//        then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.getProductsByProducer(null));
        assertEquals("Enter correct value!", exception.getMessage());
    }
    @DisplayName("testing finding products in price range")
    @Test
    void shouldReturnListOfProductsByPriceRange() {
//        given
        Product product2 = Product.builder()
                .name("Laptop Asus")
                .description("Powerful and portable laptop with stunning display.")
                .price(new BigDecimal("1000.00"))
                .category(category)
                .producer(producer)
                .amountInMagazine(10)
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .build();
        ProductDto product2Dto = ProductDto.matToDto(product2);
        when(productRepository.findByPriceRange(new BigDecimal("500.00"), new BigDecimal("2000.00"))).thenReturn(List.of(product,product2));
//        when
        List<ProductDto> products = productService.getProductsByPriceRange(new BigDecimal("500.00"), new BigDecimal("2000.00"));
//        then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(2);
        assertThat(products.get(0).getId()).isEqualTo(product.getId());
        assertThat(products.get(1).getId()).isEqualTo(product2.getId());
    }
    @DisplayName("testing finding products by price range when products does not exist")
    @Test
    void shouldReturnEmptyListOfProductsByPriceRange() {
//        given
        when(productRepository.findByPriceRange(new BigDecimal("500.00"), new BigDecimal("2000.00"))).thenReturn(Collections.emptyList());
//        when
        List<ProductDto> products = productService.getProductsByPriceRange(new BigDecimal("500.00"), new BigDecimal("2000.00"));
//        then
        assertThat(products).isEmpty();
    }
    @DisplayName("testing finding products by price range when arguments are illegal")
    @Test
    void shouldThrowExceptionWhenArgumentsAreIllegalByPriceRange() {
//        given
        BigDecimal price = product.getPrice();
        when(productRepository.findByPriceRange(null, price)).thenThrow(IllegalArgumentException.class);
//        when
//        then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.getProductsByPriceRange(null, price));
        assertEquals("Enter correct value!", exception.getMessage());
    }
}
