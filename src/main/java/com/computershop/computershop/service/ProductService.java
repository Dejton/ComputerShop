package com.computershop.computershop.service;

import com.computershop.computershop.entity.Product;
import com.computershop.computershop.entity.dto.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductService {
   Product addProduct(ProductDto productDto);
}
