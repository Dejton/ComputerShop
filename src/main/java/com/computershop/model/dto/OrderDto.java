package com.computershop.model.dto;

import com.computershop.model.entity.Order;
import com.computershop.model.entity.OrderedProduct;
import com.computershop.model.entity.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class OrderDto {
    private final Long id;
    private final User user;
    private final List<OrderedProduct> orderedProducts;
    private final String deliveryAddress;
    private final BigDecimal totalValue;
    private final LocalDate dateOfOrder;
    private final String status;

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
