package com.computershop.model.dto;

import lombok.*;


@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ProductIdAndQuantityDto {
    private final Long id;
    private final int quantity;
}
