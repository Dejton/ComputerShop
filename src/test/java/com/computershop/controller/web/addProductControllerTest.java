package com.computershop.controller.web;

import static org.assertj.core.api.Assertions.assertThat;

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