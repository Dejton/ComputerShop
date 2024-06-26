package com.computershop.controller.rest;

import com.computershop.model.entity.Order;
import com.computershop.model.entity.OrderedProduct;
import com.computershop.model.entity.User;
import com.computershop.model.dto.OrderDto;
import com.computershop.service.OrderService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(OrderController.class)
@WithMockUser
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
                .id(1L)
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
                .with(csrf())
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
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk());
    }
}





















