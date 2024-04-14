package com.computershop.repository;

import com.computershop.TestBase;
import com.computershop.model.entity.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class ProducerRepositoryTest extends TestBase {
    @Autowired
    private ProducerRepository producerRepository;
    private Producer producer;

    @BeforeEach
    void setUp() {
        producerRepository.deleteAll();

        producer = Producer.builder()
                .name("Dell")
                .build();
    }

    @DisplayName("testing adding new Producer")
    @Test
    void shouldReturnSavedProducer() {
//        given
//        when
        Producer savedProducer = producerRepository.save(producer);
//        then
        assertThat(savedProducer).isNotNull();
        assertThat(savedProducer.getId()).isEqualTo(producer.getId());
    }

    @DisplayName("testing deleting by id existing producer")
    @Test
    void shouldDeleteByIdExistingProducer() {
//        given
        producerRepository.save(producer);
//        when
        producerRepository.deleteById(producer.getId());
        Optional<Producer> byId = producerRepository.findById(producer.getId());
//        then
        assertThat(byId).isEmpty();
    }

    @DisplayName("testing finding all producers")
    @Test
    void shouldReturnListOfAllProducers() {
//        given
        producerRepository.save(producer);
//        when
        List<Producer> producers = producerRepository.findAll();
//        then
        assertThat(producers.size()).isEqualTo(1);
        assertThat(producers).isNotEmpty();
        assertThat(producers.get(0).getId()).isEqualTo(producer.getId());
    }
    @DisplayName("testing finding all producers when there is no producers")
    @Test
    void shouldReturnEmptyListOfProducers() {
//        given
//        when
        List<Producer> producers = producerRepository.findAll();
//        then
        assertThat(producers).isEmpty();
    }
    @DisplayName("testing producer finding producer by id")
    @Test
    void shouldReturnProducerById() {
//        given
        producerRepository.save(producer);
//        when
        Optional<Producer> byId = producerRepository.findById(producer.getId());
//        then
        assertThat(byId).isNotNull();
        assertThat(byId.get().getId()).isEqualTo(producer.getId());
    }

}



































