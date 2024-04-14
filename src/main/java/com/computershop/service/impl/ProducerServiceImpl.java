package com.computershop.service.impl;

import com.computershop.model.entity.Producer;
import com.computershop.model.dto.ProducerDto;
import com.computershop.repository.ProducerRepository;
import com.computershop.service.ProducerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerServiceImpl implements ProducerService {
    private final ProducerRepository producerRepository;
    public ProducerServiceImpl(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    @Override
    public Producer addProducer(ProducerDto producerDto) {
        Producer producer = ProducerDto.mapFromDto(producerDto);
        return producerRepository.save(producer);
    }

    @Override
    public void deleteProducerById(long id) {
        producerRepository.deleteById(id);
    }

    @Override
    public Producer editProducerById(long id, ProducerDto editedProducerDto) {
        Producer producerToEdit = producerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Producer with id: " + id + "not found"));
        Producer editedProducer = ProducerDto.mapFromDto(editedProducerDto);

        producerToEdit.setName(editedProducer.getName());
        if (editedProducer.getName().isBlank()) {
            throw new IllegalArgumentException("name cannot be empty!");
        }
        return producerRepository.save(producerToEdit);
    }

    @Override
    public List<ProducerDto> getAllProducers() {
        List<Producer> producers = producerRepository.findAll();
        return producers.stream()
                .map(ProducerDto::mapToDto)
                .toList();
    }

    @Override
    public ProducerDto getProducerById(long id) {
        if (id < 0) throw new IllegalArgumentException("Enter correct value!");
        return ProducerDto.mapToDto(producerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("producer with id: " + id + " not found!")));
    }

}
