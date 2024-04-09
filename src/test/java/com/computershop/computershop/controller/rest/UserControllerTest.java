package com.computershop.computershop.controller.rest;

import com.computershop.computershop.entity.User;
import com.computershop.computershop.entity.dto.UserDto;
import com.computershop.computershop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    private User user;
    private UserDto userDto;
    @BeforeEach
    void setUp() {
        user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .login("john_doe")
                .email("john@example.com")
                .address("123 Main Street")
                .role("user")
                .build();
        userDto = UserDto.mapToDto(user);
    }
    @DisplayName("testing adding new user")
    @Test
    void shouldReturnAddedUser() throws Exception {
//        given
        given(userService.addUser(userDto)).willReturn(user);
//        when
        ResultActions response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isCreated());
    }
    @DisplayName("testing adding user when login is already exists")
    @Test
    void shouldReturnBadResponseAboutLogin() throws Exception {
//        given
        User user2 = User.builder()
                .firstName("John")
                .lastName("Doe")
                .login("john_doe")
                .email("joh2n@example.com")
                .address("123 Main Street")
                .role("user")
                .build();
        UserDto user2dto = UserDto.mapToDto(user2);
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(userDto));
//        when
        ResultActions response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user2dto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("User with login: " + userDto.getLogin() + " already exists!")));
    }
    @DisplayName("testing adding user when login is already exists")
    @Test
    void shouldReturnBadResponseAboutEmail() throws Exception {
//        given
        User user2 = User.builder()
                .firstName("John")
                .lastName("Doe")
                .login("joh2n_doe")
                .email("john@example.com")
                .address("123 Main Street")
                .role("user")
                .build();
        UserDto user2dto = UserDto.mapToDto(user2);
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(userDto));
//        when
        ResultActions response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user2dto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("User with email: " + userDto.getEmail() + " already exists!")));
    }
    @DisplayName("testing getting all users")
    @Test
    void shouldReturnListOfAllProducers() throws Exception {
//        given
        given(userService.getAllUsers()).willReturn(List.of(userDto));
//        when
        ResultActions response = mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id").value(userDto.getId()));
    }
    @DisplayName("testing getting user by id")
    @Test
    void shouldReturnProducerById() throws Exception {
//        given
        given(userService.getUserById(anyLong())).willReturn(userDto);
//        when
        ResultActions response = mockMvc.perform(get("/api/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
    }
    @DisplayName("testing deleting user by id")
    @Test
    void shouldDeleteProducerById() throws Exception {
//        given
        willDoNothing().given(userService).deleteUserById(user.getId());
//        when
        ResultActions response = mockMvc.perform(delete("/api/users/{id}", userDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
        );
//        then
        response.andDo(print())
                .andExpect(status().isOk());
    }
}