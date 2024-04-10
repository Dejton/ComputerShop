package com.computershop.computershop.controller.web;

import com.computershop.computershop.controller.rest.UserController;
import com.computershop.computershop.entity.dto.UserDto;
import com.computershop.computershop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final  UserService userService;
    private final UserController userController;

    public RegistrationController(UserService userService, UserController userController) {
        this.userService = userService;
        this.userController = userController;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
       return "registration";
    }
    @PostMapping
    public String addUserToDB(@ModelAttribute UserDto userDto, Model model) {
        userDto.setRole("USER");
        ResponseEntity<String> response = userController.addUser(userDto);

        if (response.getBody().contains("already exists!")) {
            if (response.getBody().contains("email")) {
                model.addAttribute("emailError", "Email already exists");
                model.addAttribute("emailValue", userDto.getEmail());
            }
            if (response.getBody().contains("login")) {
                model.addAttribute("loginError", "Login already exists");
                model.addAttribute("loginValue", userDto.getLogin());
            }
            model.addAttribute("firstName", userDto.getFirstName());
            model.addAttribute("lastName", userDto.getLastName());
            model.addAttribute("login", userDto.getLogin());
            model.addAttribute("password", userDto.getPassword());
            model.addAttribute("email", userDto.getEmail());
            model.addAttribute("address", userDto.getAddress());
            return "registration";
        }

        return "redirect:/";
    }
}
