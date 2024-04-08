package com.computershop.computershop.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ProductIdAndQuantityDto {
    private Long id;
    private int quantity;
}
