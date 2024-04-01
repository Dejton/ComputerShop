package com.computershop.computershop.service.impl;

import com.computershop.computershop.entity.Product;
import com.computershop.computershop.entity.dto.ProductDto;
import com.computershop.computershop.repository.ProductRepository;
import com.computershop.computershop.service.ProducerService;
import com.computershop.computershop.service.ProductService;
import org.springframework.stereotype.Service;

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
}
