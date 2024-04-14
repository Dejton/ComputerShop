package com.computershop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
public class ProductIdAndQuantityDto {
    private final Long id;
    private final int quantity;
}
