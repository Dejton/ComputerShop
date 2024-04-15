package com.computershop.model.dto;

import com.computershop.model.entity.Category;
import lombok.*;


@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class CategoryDto {
    private final Long id;
    private final String name;

    public static CategoryDto mapToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category mapFromDto(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }
}
