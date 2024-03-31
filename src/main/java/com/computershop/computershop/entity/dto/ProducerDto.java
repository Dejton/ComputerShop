package com.computershop.computershop.entity.dto;

import com.computershop.computershop.entity.Producer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProducerDto {
    private long id;
    private String name;

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
