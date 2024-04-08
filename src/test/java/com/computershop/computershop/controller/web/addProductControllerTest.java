package com.computershop.computershop.controller.web;

import com.computershop.computershop.controller.rest.ProductController;
import com.computershop.computershop.entity.dto.CategoryDto;
import com.computershop.computershop.entity.dto.ProducerDto;
import com.computershop.computershop.entity.dto.ProductDto;
import com.computershop.computershop.service.CategoryService;
import com.computershop.computershop.service.ProducerService;
import com.computershop.computershop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class addProductControllerTest {
//    @Mock
//    private CategoryService categoryService;
//
//    @Mock
//    private ProducerService producerService;
//
//    @Mock
//    private ProductService productService;
//
//    @InjectMocks
//    private ProductController productController;
//
//    @Test
//    public void testShowAddProductForm() {
//        List<CategoryDto> categories = new ArrayList<>();
//        categories.add(new CategoryDto("Category1"));
//        categories.add(new CategoryDto("Category2"));
//
//        List<ProducerDto> producers = new ArrayList<>();
//        producers.add(new ProducerDto("Producer1"));
//        producers.add(new ProducerDto("Producer2"));
//
//        Model model = mock(Model.class);
//
//        when(categoryService.getAllCategories()).thenReturn(categories);
//        when(producerService.getAllProducers()).thenReturn(producers);
//
//        String viewName = productController.showAddProductForm(model);
//
//        assertThat(viewName).isEqualTo("addProduct");
//        verify(model, times(1)).addAttribute("categories", categories);
//        verify(model, times(1)).addAttribute("producers", producers);
//    }
//
//    @Test
//    public void testAddProduct() {
//        ProductDto productDto = new ProductDto();
//        when(productService.addProduct(productDto)).thenReturn(productDto);
//
//        Model model = mock(Model.class);
//
//        String viewName = productController.addProduct(productDto, model);
//
//        assertThat(viewName).isEqualTo("redirect:/");
//        verify(productService, times(1)).addProduct(productDto);
//    }

}