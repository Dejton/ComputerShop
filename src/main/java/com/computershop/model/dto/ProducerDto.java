package com.computershop.model.dto;

import com.computershop.model.entity.Producer;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class ProducerDto {
    private final Long id;
    private final String name;

    public static ProducerDto mapToDto(Producer producer) {
        return ProducerDto.builder()
                .id(producer.getId())
                .name(producer.getName())
                .build();
    }
    public static Producer mapFromDto(ProducerDto producerDto) {
        return Producer.builder()
                .id(producerDto.getId())
                .name(producerDto.getName())
                .build();
    }
}
