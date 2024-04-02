package com.computershop.computershop.service;

import com.computershop.computershop.entity.Category;
import com.computershop.computershop.entity.Producer;
import com.computershop.computershop.entity.Product;
import com.computershop.computershop.entity.dto.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
   Product addProduct(ProductDto productDto);
   void deleteProductById(long id);
   Product editProductById(long id, ProductDto updatedProductDto);
   List<ProductDto> getAllProducts();
   ProductDto getProductByName(String name);
   ProductDto getProductById(long id);
   List<ProductDto> getProductsByPrice(BigDecimal price);
   List<ProductDto> getProductsByCategory(Category category);
   List<ProductDto> getProductsByProducer(Producer producer);
   List<ProductDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
}
