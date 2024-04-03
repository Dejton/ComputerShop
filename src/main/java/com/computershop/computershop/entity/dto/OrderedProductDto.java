package com.computershop.computershop.entity.dto;

import com.computershop.computershop.entity.Order;
import com.computershop.computershop.entity.OrderedProduct;
import com.computershop.computershop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderedProductDto {
    private long id;
    private int quantity;
    private Product product;
    private Order order;
    private BigDecimal price;

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
