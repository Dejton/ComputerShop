package com.computershop.service;

import com.computershop.model.entity.Category;
import com.computershop.model.entity.Producer;
import com.computershop.model.entity.Product;
import com.computershop.model.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
   Product addProduct(ProductDto productDto);
   void deleteProductById(Long id);
   Product editProductById(Long id, ProductDto updatedProductDto);
   List<ProductDto> getAllProducts();
   ProductDto getProductByName(String name);
   ProductDto getProductById(Long id);
   List<ProductDto> getProductsByPrice(BigDecimal price);
   List<ProductDto> getProductsByCategory(Category category);
   List<ProductDto> getProductsByProducer(Producer producer);
   List<ProductDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
}
