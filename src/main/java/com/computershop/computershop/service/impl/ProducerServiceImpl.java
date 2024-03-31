package com.computershop.computershop.service.impl;

import com.computershop.computershop.entity.Producer;
import com.computershop.computershop.entity.dto.ProducerDto;
import com.computershop.computershop.repository.ProducerRepository;
import com.computershop.computershop.service.ProducerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if (editedProducer.getName().isEmpty()) {
            throw new IllegalArgumentException("name can't be empty!");
        }
        return producerRepository.save(producerToEdit);
    }

    @Override
    public List<ProducerDto> findAllProducers() {
        List<Producer> producers = producerRepository.findAll();
        return producers.stream()
                .map(ProducerDto::mapToDto)
                .toList();
    }

//    -------------------------- dorobić metodę do wyszukiwania produktów po producencie
}
