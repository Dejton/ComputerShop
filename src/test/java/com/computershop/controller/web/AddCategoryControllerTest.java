package com.computershop.controller.web;

import com.computershop.model.dto.CategoryDto;
import com.computershop.model.entity.Category;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddCategoryController.class)
@WithMockUser(roles = {"ADMIN"})
@Import(WebSecurityConfig.class)
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
        ResultActions response = mockMvc.perform(post("/add-category")
                .with(csrf())
                .param("name", categoryDto.getName()));

//        then
        response.andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(categoryService, times(1)).addCategory(categoryDto);

    }


}