package com.computershop.computershop.entity.dto;

import com.computershop.computershop.entity.Order;
import com.computershop.computershop.entity.OrderedProduct;
import com.computershop.computershop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDto {
    private long id;
    private User user;
    private List<OrderedProduct> orderedProducts;
    private String deliveryAddress;
    private BigDecimal totalValue;
    private LocalDate dateOfOrder;
    private String status;

    public static OrderDto mapToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .user(order.getUser())
                .orderedProducts(order.getOrderedProducts())
                .deliveryAddress(order.getDeliveryAddress())
                .totalValue(order.getTotalValue())
                .dateOfOrder(order.getDateOfOrder())
                .status(order.getStatus())
                .build();
    }
    public static Order mapFromDto(OrderDto orderDto) {
        return Order.builder()
                .id(orderDto.getId())
                .user(orderDto.getUser())
                .orderedProducts(orderDto.getOrderedProducts())
                .deliveryAddress(orderDto.getDeliveryAddress())
                .totalValue(orderDto.getTotalValue())
                .dateOfOrder(orderDto.getDateOfOrder())
                .status(orderDto.getStatus())
                .build();
    }
}
