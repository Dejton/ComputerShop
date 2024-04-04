package com.computershop.computershop.controller.rest;

import com.computershop.computershop.entity.Product;
import com.computershop.computershop.entity.dto.ProductDto;
import com.computershop.computershop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }
    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable long id) {
        productService.deleteProductById(id);
    }
}
