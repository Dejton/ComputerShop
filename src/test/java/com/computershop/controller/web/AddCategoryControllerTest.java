package com.computershop.controller.web;

import com.computershop.model.dto.CategoryDto;
import com.computershop.model.entity.Category;
import com.computershop.model.entity.User;
import com.computershop.model.dto.UserDto;
import com.computershop.service.CategoryService;
import com.computershop.service.UserService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddCategoryController.class)
@WithMockUser("ROLE_ADMIN")
class AddCategoryControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    private Category category;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .name("Laptopy")
                .build();
        categoryDto = CategoryDto.mapToDto(category);
    }

    @DisplayName("Test adding a new category")
    @Test
    void testAddNewCategory() throws Exception {
        // Given
        given(categoryService.addCategory(any(CategoryDto.class))).willReturn(category);
        // When
        ResultActions response = mockMvc.perform(post("/api/categories")
                .with(csrf())
                .param("name", categoryDto.getName()));

//        then
        response.andDo(print())
                .andExpect(status().isOk());
        verify(categoryService, times(1)).addCategory(categoryDto);

    }


}