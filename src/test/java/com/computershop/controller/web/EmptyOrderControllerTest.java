package com.computershop.controller.web;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmptyOrderController.class)
class EmptyOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Test displaying empty order page")
    @Test
    void testShowEmptyOrderPage() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(get("/empty-order"));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("emptyOrder"));
    }
}