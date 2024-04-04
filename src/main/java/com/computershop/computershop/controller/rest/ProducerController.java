package com.computershop.computershop.controller.rest;

import com.computershop.computershop.entity.Producer;
import com.computershop.computershop.entity.dto.ProducerDto;
import com.computershop.computershop.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producers")
public class ProducerController {
    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producer addProducer(@RequestBody ProducerDto producerDto) {
        return producerService.addProducer(producerDto);
    }
    @GetMapping
    public List<ProducerDto> getAllProducers() {
        return producerService.getAllProducers();
    }
    @GetMapping("/{id}")
    public ProducerDto getProducerById(@PathVariable long id) {
        return producerService.getProducerById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteProducerById(@PathVariable long id) {
        producerService.deleteProducerById(id);
    }
}
