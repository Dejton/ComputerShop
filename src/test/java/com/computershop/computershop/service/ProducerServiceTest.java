package com.computershop.computershop.service;

import com.computershop.computershop.entity.Producer;
import com.computershop.computershop.entity.dto.ProducerDto;
import com.computershop.computershop.repository.ProducerRepository;
import com.computershop.computershop.service.impl.ProducerServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DataJpaTest
class ProducerServiceTest {
    private final ProducerRepository producerRepository = mock(ProducerRepository.class);
    private final ProducerServiceImpl producerService = new ProducerServiceImpl(producerRepository);
    private Producer producer;
    private ProducerDto producerDto;

    @BeforeEach
    void setUp() {
        producerRepository.deleteAll();

        producer = Producer.builder()
                .name("Dell")
                .build();
        producerDto = ProducerDto.mapToDto(producer);
    }

    @DisplayName("testing adding new producer")
    @Test
    void shouldReturnSavedProducer() {
//        given
        when(producerRepository.save(any(Producer.class))).thenReturn(producer);
//        when
        Producer savedProducer = producerService.addProducer(producerDto);
//        then
        assertThat(savedProducer).isNotNull();
        assertThat(savedProducer.getId()).isEqualTo(producer.getId());
    }

    @DisplayName("testing adding new producer when there is producer with the same name")
    @Test
    void shouldThrowExceptionWhenNameIsTheSame() {
//        given
        Producer producer2 = Producer.builder()
                .name("Dell")
                .build();
        ProducerDto producer2Dto = ProducerDto.mapToDto(producer2);
        when(producerRepository.save(any(Producer.class))).thenThrow(DataIntegrityViolationException.class);
//        when
//        then
        assertThrows(DataIntegrityViolationException.class, () -> producerService.addProducer(producer2Dto));
    }

    @DisplayName("testing deleting producer by id")
    @Test
    void shouldDeleteProducerById() {
//        given
        when(producerRepository.save(any(Producer.class))).thenReturn(producer);
//        when
        producerService.deleteProducerById(producer.getId());
//        then
        verify(producerRepository).deleteById(producer.getId());
    }
    @DisplayName("testing editing producer by id")
    @Test
    void shouldReturnEditedProducerById() {
//        given
        Producer editedProducer = Producer.builder()
                .name("Asus")
                .build();
        ProducerDto editedProducerDto = ProducerDto.mapToDto(editedProducer);
        when(producerRepository.findById(anyLong())).thenReturn(Optional.of(producer));
        when(producerRepository.save(any(Producer.class))).thenReturn(producer);
//        when
        Producer producerToEdit = producerService.editProducerById(producer.getId(), editedProducerDto);
//        then
        assertThat(producerToEdit).isNotNull();
        assertThat(producerToEdit.getId()).isEqualTo(producer.getId());
    }
    @DisplayName("testing editing producer when there is no producer with that id")
    @Test
    void shouldThrowExceptionWhenThereIsNoProducerById() {
//        given
        Producer editedProducer = Producer.builder()
                .name("Asus")
                .build();
        ProducerDto editedProducerDto = ProducerDto.mapToDto(editedProducer);
        when(producerRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);
//        when
//        then
        assertThrows(EntityNotFoundException.class, () -> producerService.editProducerById(producer.getId(), editedProducerDto));
    }
    @DisplayName("testing editing producer when name is the same like already exist")
    @Test
    void shouldThrowExceptionWhenNameIsTheSameForEditingById() {
//        given
        Producer editedProducer = Producer.builder()
                .name("Asus")
                .build();
        ProducerDto editedProducerDto = ProducerDto.mapToDto(editedProducer);
        when(producerRepository.findById(anyLong())).thenReturn(Optional.of(producer));
        when(producerRepository.save(any(Producer.class))).thenThrow(DataIntegrityViolationException.class);
//        when
//        then
        assertThrows(DataIntegrityViolationException.class, () -> producerService.editProducerById(producer.getId(), editedProducerDto));
    }
    @DisplayName("testing Editing producer WhenName Is Empty")
    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
//        given
        Producer editedProducer = Producer.builder()
                .name("  ")
                .build();
        ProducerDto editedProducerDto = ProducerDto.mapToDto(editedProducer);
        when(producerRepository.findById(anyLong())).thenReturn(Optional.of(producer));
        when(producerRepository.save(any(Producer.class))).thenThrow(IllegalArgumentException.class);
//        when
//        then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> producerService.editProducerById(producer.getId(), editedProducerDto));
        assertEquals("name cannot be empty!", exception.getMessage());
    }
    @DisplayName("testing finding all producers")
    @Test
    void shouldReturnListOfAllProducers() {
//        given
        when(producerRepository.findAll()).thenReturn(List.of(producer));
//        when
        List<ProducerDto> producers = producerService.findAllProducers();
//        then
        assertThat(producers).isNotEmpty();
        assertThat(producers.size()).isEqualTo(1);
        assertThat(producers.get(0).getId()).isEqualTo(producer.getId());
    }
}












































