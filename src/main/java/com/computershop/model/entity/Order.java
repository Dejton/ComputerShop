package com.computershop.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderedProduct> orderedProducts = new ArrayList<>();
    @Column(nullable = false, name = "delivery_address")
    private String deliveryAddress;
    @Column(nullable = false, name = "total_value")
    private BigDecimal totalValue;
    @Column(nullable = false, name = "date_of_order")
    private LocalDate dateOfOrder;
    @Column(nullable = false)
    private String status;
}
