package com.computershop.model.dto;

import com.computershop.model.entity.Order;
import com.computershop.model.entity.OrderedProduct;
import com.computershop.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class OrderedProductDto {
    private final long id;
    private final int quantity;
    private final Product product;
    private final Order order;
    private final BigDecimal price;

    public static OrderedProductDto matToDto(OrderedProduct orderedProduct) {
        return OrderedProductDto.builder()
                .id(orderedProduct.getId())
                .quantity(orderedProduct.getQuantity())
                .product(orderedProduct.getProduct())
                .order(orderedProduct.getOrder())
                .price(orderedProduct.getPrice())
                .build();
    }
    public static OrderedProduct matFromDto(OrderedProductDto orderedProductDto) {
        return OrderedProduct.builder()
                .id(orderedProductDto.getId())
                .quantity(orderedProductDto.getQuantity())
                .product(orderedProductDto.getProduct())
                .order(orderedProductDto.getOrder())
                .price(orderedProductDto.getPrice())
                .build();
    }
}
