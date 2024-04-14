package com.computershop.service;

import com.computershop.model.entity.Producer;
import com.computershop.model.dto.ProducerDto;

import java.util.List;

public interface ProducerService {
    Producer addProducer(ProducerDto producerDto);
    void deleteProducerById(long id);
    Producer editProducerById(long id, ProducerDto editedProducerDto);
    List<ProducerDto> getAllProducers();
    ProducerDto getProducerById(long id);
}
