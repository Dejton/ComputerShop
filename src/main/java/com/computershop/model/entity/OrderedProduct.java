package com.computershop.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Builder
@Table(name = "ordered_products")
public class OrderedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
