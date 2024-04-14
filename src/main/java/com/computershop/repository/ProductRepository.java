package com.computershop.repository;

import com.computershop.model.entity.Category;
import com.computershop.model.entity.Producer;
import com.computershop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByName(String name);
    List<Product> findByPrice(BigDecimal price);
    @Query(value = "SELECT * FROM products p WHERE p.price >= ?1 AND p.price <= ?2", nativeQuery = true)
    List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> findByCategory(Category category);
    List<Product> findByProducer(Producer producer);
}
