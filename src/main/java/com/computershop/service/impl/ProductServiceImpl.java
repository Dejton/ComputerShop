package com.computershop.service.impl;

import com.computershop.model.entity.Category;
import com.computershop.model.entity.Producer;
import com.computershop.model.entity.Product;
import com.computershop.model.dto.ProductDto;
import com.computershop.repository.ProductRepository;
import com.computershop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProduct(ProductDto productDto) {
        Product product = ProductDto.matFromDto(productDto);
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product editProductById(Long id, ProductDto updatedProductDto) {
        Product productToEdit = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + "not found."));
        productToEdit.setName(updatedProductDto.getName());
        productToEdit.setDescription(updatedProductDto.getDescription());
        productToEdit.setPrice(updatedProductDto.getPrice());
        productToEdit.setCategory(updatedProductDto.getCategory());
        productToEdit.setProducer(updatedProductDto.getProducer());
        productToEdit.setAmountInMagazine(updatedProductDto.getAmountInMagazine());
        productToEdit.setImages(updatedProductDto.getImages());

        if (updatedProductDto.getName().isBlank()) {
            throw new IllegalArgumentException("name cannot be empty!");
        }
        return productRepository.save(productToEdit);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductDto::matToDto)
                .toList();
    }

    @Override
    public ProductDto getProductByName(String name) {
        if (name.isBlank()) throw new IllegalArgumentException("Invalid name value!");
        return ProductDto.matToDto(productRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("product with this name does not exist!")));
    }

    @Override
    public ProductDto getProductById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Enter correct value!");
        }
        return ProductDto.matToDto(productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("product with id: " + id + "not found.")));
    }

    @Override
    public List<ProductDto> getProductsByPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Enter correct value!");
        List<Product> products = productRepository.findByPrice(price);
        return products.stream()
                .map(ProductDto::matToDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByCategory(Category category) {
        if (category == null) throw new IllegalArgumentException("Enter correct value!");
        List<Product> products = productRepository.findByCategory(category);
        return products.stream()
                .map(ProductDto::matToDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByProducer(Producer producer) {
        if (producer == null) throw new IllegalArgumentException("Enter correct value!");
        List<Product> products = productRepository.findByProducer(producer);
        return products.stream()
                .map(ProductDto::matToDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice == null || maxPrice == null || minPrice.compareTo(BigDecimal.ZERO) < 0 || minPrice.compareTo(maxPrice) > 0) throw new IllegalArgumentException("Enter correct value!");
        List<Product> products = productRepository.findByPriceRange(minPrice, maxPrice);
        return products.stream()
                .map(ProductDto::matToDto)
                .toList();
    }
}


















