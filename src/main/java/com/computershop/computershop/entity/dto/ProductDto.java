package com.computershop.computershop.entity.dto;

import com.computershop.computershop.entity.Category;
import com.computershop.computershop.entity.Producer;
import com.computershop.computershop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDto {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Category category;
    private Producer producer;
    private int amountInMagazine;
    private List<String> images;

    public static ProductDto matToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .producer(product.getProducer())
                .amountInMagazine(product.getAmountInMagazine())
                .images(product.getImages())
                .build();
    }

    public static Product matFromDto(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .category(productDto.getCategory())
                .producer(productDto.getProducer())
                .amountInMagazine(productDto.getAmountInMagazine())
                .images(productDto.getImages())
                .build();
    }
}
