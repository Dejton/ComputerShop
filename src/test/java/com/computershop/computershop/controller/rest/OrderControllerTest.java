package com.computershop.computershop.controller.rest;

import com.computershop.computershop.entity.Order;
import com.computershop.computershop.entity.OrderedProduct;
import com.computershop.computershop.entity.User;
import com.computershop.computershop.entity.dto.OrderDto;
import com.computershop.computershop.entity.dto.OrderedProductDto;
import com.computershop.computershop.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    private Order order;
    private OrderDto orderDto;
    private User user;
    private List<OrderedProduct> orderedProducts;
    @BeforeEach
    void setUp() {
        order = Order.builder()
                .user(user)
                .orderedProducts(orderedProducts)
                .deliveryAddress("123 Main Street")
                .totalValue(BigDecimal.valueOf(100))
                .dateOfOrder(LocalDate.now())
                .status("pending")
                .build();
    }

    @DisplayName("testing adding new order")
    @Test
    void shouldReturnAddedOrder() throws Exception {
//        given
        orderDto = OrderDto.mapToDto(order);
        given(orderService.addOrder(any(OrderDto.class))).willReturn(order);

//        when
        ResultActions response = mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(orderDto)));
    }
    @DisplayName("testing getting all orders")
    @Test
    void shouldReturnListOfAllOrders() throws Exception {
//        given
        orderDto = OrderDto.mapToDto(order);
        given(orderService.getAllOrders()).willReturn(List.of(orderDto));
//        when
        ResultActions response = mockMvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id").value(orderDto.getId()));
    }
    @DisplayName("testing getting order by id")
    @Test
    void shouldReturnOrderById() throws Exception {
//        given
        orderDto = OrderDto.mapToDto(order);
        given(orderService.getOrderById(anyLong())).willReturn(orderDto);
//        when
        ResultActions response = mockMvc.perform(get("/api/orders/{id}", orderDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(orderDto)));
    }
    @DisplayName("testing deleting order by id")
    @Test
    void shouldDeleteOrderById() throws Exception {
//        given
        willDoNothing().given(orderService).deleteOrderById(order.getId());
//        when
        ResultActions response = mockMvc.perform(delete("/api/orders/{id}", order.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk());
    }
}





















