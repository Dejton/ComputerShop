package com.computershop.service;

import com.computershop.model.entity.User;
import com.computershop.model.dto.UserDto;
import com.computershop.repository.UserRepository;
import com.computershop.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserServiceImpl userService = new UserServiceImpl(userRepository);
    private User user;
    private UserDto userDto;
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        user = User.builder()
                .id(1L)
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
    void shouldReturnSavedUser() {
//        given
        when(userRepository.save(any(User.class))).thenReturn(user);
//        when
        User savedUser = userService.addUser(userDto);
//        then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo(user.getId());
    }
    @DisplayName("testing adding new user when login is occupied")
    @Test
    void shouldThrowExceptionWhenLoginIsOccupied() {
//        given
        User user2 = User.builder()
                .firstName("John")
                .lastName("Doe")
                .login("john_doe")
                .email("john3@example.com")
                .address("123 Main Street")
                .role("user")
                .build();
        UserDto user2Dto = UserDto.mapToDto(user2);
        when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);
//        when
//        then
        assertThrows(DataIntegrityViolationException.class, () -> userService.addUser(user2Dto));
    }
    @DisplayName("testing adding new user when email is occupied")
    @Test
    void shouldThrowExceptionWhenEmailIsOccupied() {
//        given
        User user2 = User.builder()
                .firstName("John2")
                .lastName("Doe")
                .login("john_doe")
                .email("john@example.com")
                .address("123 Main Street")
                .role("user")
                .build();
        UserDto user2Dto = UserDto.mapToDto(user2);
        when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);
//        when
//        then
        assertThrows(DataIntegrityViolationException.class, () -> userService.addUser(user2Dto));
    }
    @DisplayName("testing deleting user by id")
    @Test
    void shouldDeleteUserById() {
//        given
        when(userRepository.save(any(User.class))).thenReturn(user);
//        when
        userService.deleteUserById(user.getId());
//        then
        verify(userRepository).deleteById(user.getId());
    }
    @DisplayName("testing finding all users")
    @Test
    void shouldReturnListOfAllUsers() {
//        given
        when(userRepository.findAll()).thenReturn(List.of(user));
//        when
        List<UserDto> users = userService.getAllUsers();
//        then
        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getId()).isEqualTo(user.getId());
    }
    @DisplayName("testing finding user by id")
    @Test
    void shouldReturnUserById() {
//        given
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
//        when
        UserDto foundUser = userService.getUserById(user.getId());
//        then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(user.getId());
    }
    @DisplayName("testing finding user by id when does not exist")
    @Test
    void shouldThrowExceptionWhenUserDoesNotExistById() {
//        given
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
//        when
//        then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.getUserById(user.getId()));
        assertEquals("User with id: " + user.getId() + "does not exist!", exception.getMessage());
    }
    @DisplayName("testing finding user by id when argument is illegal")
    @Test
    void shouldThrowExceptionWhenArgumentIsIllegal() {
//        given
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
//        when
//        then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserById(-10));
        assertEquals("Enter correct value!", exception.getMessage());
    }
    @DisplayName("testing finding user by login")
    @Test
    void shouldReturnUserByLogin() {
//        given
        when(userRepository.findByLogin(anyString())).thenReturn(Optional.of(user));
//        when
        User foundUser = userService.getUserByLogin(user.getLogin());
//        then
        assertThat(foundUser).isEqualTo(user);
    }

}
















