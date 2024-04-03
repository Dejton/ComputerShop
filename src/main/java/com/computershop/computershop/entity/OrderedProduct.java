package com.computershop.computershop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "ordered_products")
public class OrderedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private int quantity;
    @ManyToOne
    @JoinColumn(nullable = false, name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(nullable = false, name = "order_id")
    private Order order;
    @Column(nullable = false)
    private BigDecimal price;
}
