package com.computershop.controller.rest;

import com.computershop.model.dto.UserDto;
import com.computershop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        List<UserDto> allUsers = userService.getAllUsers();
        boolean isPresentByLogin = allUsers.stream()
                .anyMatch(userDto1 -> userDto1.getLogin().equals(userDto.getLogin()));
        boolean isPresentByEmail = allUsers.stream()
                .anyMatch(userDto1 -> userDto1.getEmail().equals(userDto.getEmail()));
        if (isPresentByEmail) {
            return ResponseEntity.badRequest().body("User with email: " + userDto.getEmail() + " already exists!");
        }
        if (isPresentByLogin) {
            return ResponseEntity.badRequest().body("User with login: " + userDto.getLogin() + " already exists!");
        }

        userService.addUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration completed");
    }
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id) {
        userService.deleteUserById(id);
    }

}
