package com.computershop.controller.rest;

import com.computershop.model.entity.Category;
import com.computershop.model.entity.Producer;
import com.computershop.model.entity.Product;
import com.computershop.model.dto.ProductDto;
import com.computershop.security.WebSecurityConfig;
import com.computershop.service.ProductService;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductController.class)
@WithMockUser
@Import(WebSecurityConfig.class)
class ProductControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    private Product product;
    private ProductDto productDto;
    private Category category;
    private Producer producer;
    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .name("Laptop Dell XPS 13")
                .description("Powerful and portable laptop with stunning display.")
                .price(new BigDecimal("1999.99"))
                .category(category)
                .producer(producer)
                .amountInMagazine(10)
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .build();
        productDto = ProductDto.matToDto(product);
    }
    @DisplayName("testing adding new product")
    @Test
    void shouldReturnAddedProduct() throws Exception {
//        given
        given(productService.addProduct(productDto)).willReturn(product);
//        when
        ResultActions response = mockMvc.perform(post("/api/products")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(productDto)));
    }
    @DisplayName("testing getting all products")
    @Test
    void shouldReturnListOfAllProducers() throws Exception {
//        given
        given(productService.getAllProducts()).willReturn(List.of(productDto));
//        when
        ResultActions response = mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id").value(productDto.getId()));
    }
    @DisplayName("testing getting product by id")
    @Test
    void shouldReturnProducerById() throws Exception {
//        given
        given(productService.getProductById(anyLong())).willReturn(productDto);
//        when
        ResultActions response = mockMvc.perform(get("/api/products/{id}", productDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDto)));
    }
    @DisplayName("testing deleting product by id")
    @Test
    void shouldDeleteProducerById() throws Exception {
//        given
        willDoNothing().given(productService).deleteProductById(product.getId());
//        when
        ResultActions response = mockMvc.perform(delete("/api/products/{id}", productDto.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk());
    }
}