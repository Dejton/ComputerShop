package com.computershop.controller.web;

import com.computershop.controller.rest.UserController;
import com.computershop.model.dto.ProductIdAndQuantityDto;
import com.computershop.model.entity.Order;
import com.computershop.model.entity.OrderedProduct;
import com.computershop.model.entity.User;
import com.computershop.security.WebSecurityConfig;
import com.computershop.service.OrderService;
import com.computershop.service.UserService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
@WithMockUser
@Import(WebSecurityConfig.class)
class AddOrderControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    private Order order;
    private User user;
    private ProductIdAndQuantityDto productIdAndQuantityDto;
    private List<OrderedProduct> orderedProducts;
//    @BeforeEach
//    void setUp() {
//        order = Order.builder()
//                .id(1L)
//                .user(user)
//                .orderedProducts(orderedProducts)
//                .deliveryAddress("123 Main Street")
//                .totalValue(BigDecimal.valueOf(100))
//                .dateOfOrder(LocalDate.now())
//                .status("pending")
//                .build();
//    }


}