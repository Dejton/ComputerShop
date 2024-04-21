package com.computershop.controller.web;

import com.computershop.security.WebSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ErrorController.class)
@Import(WebSecurityConfig.class)
class ErrorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Test displaying error page")
    @Test
    void testShowErrorPage() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(get("/error"));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("error"));
    }
}