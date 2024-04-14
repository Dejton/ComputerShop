package com.computershop.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(nullable = false, name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(nullable = false, name = "producer_id")
    private Producer producer;
    @Column(nullable = false, name = "amount_in_magazine")
    private int amountInMagazine;
    @ElementCollection
    @CollectionTable(name = "images", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> images;
}