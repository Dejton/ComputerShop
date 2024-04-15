package com.computershop.model.dto;

import com.computershop.model.entity.Category;
import com.computershop.model.entity.Producer;
import com.computershop.model.entity.Product;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class ProductDto {
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Category category;
    private final Producer producer;
    private final int amountInMagazine;
    private final List<String> images;

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
