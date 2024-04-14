package com.computershop.repository;

import com.computershop.TestBase;
import com.computershop.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class UserRepositoryTest extends TestBase {
    @Autowired
    private UserRepository userRepository;
    private User user;
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .login("john_doe")
                .email("john@example.com")
                .address("123 Main Street")
                .role("user")
                .build();
    }

    @DisplayName("testing adding new user")
    @Test
    void shouldReturnSavedUser() {
//        given
//        when
        User savedUser = userRepository.save(user);
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
                .email("john2@example.com")
                .address("123 Main Street")
                .role("user")
                .build();
        userRepository.save(user);
//        when
//        then
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user2));
    }
    @DisplayName("testing adding new user when email is occupied")
    @Test
    void shouldThrowExceptionWhenEmailIsOccupied() {
//        given
        User user2 = User.builder()
                .firstName("John")
                .lastName("Doe")
                .login("john_doe2")
                .email("john@example.com")
                .address("123 Main Street")
                .role("user")
                .build();
        userRepository.save(user);
//        when
//        then
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user2));
    }
    @DisplayName("testing deleting user by id")
    @Test
    void shouldDeleteUserById() {
//        given
        userRepository.save(user);
//        when
        userRepository.deleteById(user.getId());
        Optional<User> foundUser = userRepository.findById(user.getId());
//        then
        assertThat(foundUser).isEmpty();
    }
    @DisplayName("testing finding all users")
    @Test
    void shouldReturnListOfAllUsers() {
//        given
        userRepository.save(user);
//        when
        List<User> users = userRepository.findAll();
//        then
        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getId()).isEqualTo(user.getId());
    }
    @DisplayName("testing finding user by id")
    @Test
    void shouldReturnUserById() {
//        given
        userRepository.save(user);
//        when
        Optional<User> foundUser = userRepository.findById(user.getId());
//        then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.get().getId()).isEqualTo(user.getId());
    }
    @DisplayName("testing finding user by id when does not exist")
    @Test
    void shouldReturnEmptyOptionalWhenDoesNotExist() {
//        given
//        when
        Optional<User> foundUser = userRepository.findById(user.getId());
//        then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser).isEmpty();
    }
}























