package com.computershop.controller.rest;

import com.computershop.model.entity.Category;
import com.computershop.model.dto.CategoryDto;
import com.computershop.security.WebSecurityConfig;
import com.computershop.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CategoryController.class)
@WithMockUser
@Import(WebSecurityConfig.class)
class CategoryControllerTest {
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
    @DisplayName("testing adding new category")
    @Test
    void shouldAddCategory() throws Exception {
//        given
        given(categoryService.addCategory(any(CategoryDto.class))).willReturn(category);
//        when
        ResultActions response = mockMvc.perform(post("/api/categories")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category))
        );
//        then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(category.getName())));
    }
    @DisplayName("testing getting all categories")
    @Test
    void shouldReturnAllCategories() throws Exception {
//        given
        given(categoryService.getAllCategories()).willReturn(List.of(categoryDto));
//        when
        ResultActions response = mockMvc.perform(get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }
    @DisplayName("testing deleting category by id")
    @Test
    void shouldDeleteCategoryById() throws Exception {
//        given
        willDoNothing().given(categoryService).deleteCategoryById(category.getId());
//        when
        ResultActions response = mockMvc.perform(delete("/api/categories/{id}", category.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk());
    }
}

















