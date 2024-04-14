package com.computershop.controller.rest;

import com.computershop.model.entity.Producer;
import com.computershop.model.dto.ProducerDto;
import com.computershop.service.ProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProducerController.class)
@WithMockUser()
class ProducerControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProducerService producerService;
    private Producer producer;
    private ProducerDto producerDto;

    @BeforeEach
    void setUp() {
        producer = Producer.builder()
                .name("Dell")
                .build();
        producerDto = ProducerDto.mapToDto(producer);
    }

    @DisplayName("testing adding new producer")
    @Test
    void shouldReturnAddedProducer() throws Exception {
//        given
        given(producerService.addProducer(producerDto)).willReturn(producer);
//        when
        ResultActions response = mockMvc.perform(post("/api/producers")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producerDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(producerDto)));
    }

    @DisplayName("testing getting all producers")
    @Test
    void shouldReturnListOfAllProducers() throws Exception {
//        given
        given(producerService.getAllProducers()).willReturn(List.of(producerDto));
//        when
        ResultActions response = mockMvc.perform(get("/api/producers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producerDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id").value(producerDto.getId()));
    }

    @DisplayName("testing getting producer by id")
    @Test
    void shouldReturnProducerById() throws Exception {
//        given
        given(producerService.getProducerById(anyLong())).willReturn(producerDto);
//        when
        ResultActions response = mockMvc.perform(get("/api/producers/{id}", producerDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producerDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(producerDto)));
    }

    @DisplayName("testing deleting producer by id")
    @Test
    void shouldDeleteProducerById() throws Exception {
//        given
        willDoNothing().given(producerService).deleteProducerById(producer.getId());
//        when
        ResultActions response = mockMvc.perform(delete("/api/producers/{id}", producerDto.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producerDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk());
    }

}















