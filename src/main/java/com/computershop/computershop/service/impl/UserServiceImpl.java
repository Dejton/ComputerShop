package com.computershop.computershop.service.impl;

import com.computershop.computershop.entity.User;
import com.computershop.computershop.entity.dto.UserDto;
import com.computershop.computershop.repository.UserRepository;
import com.computershop.computershop.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(UserDto userDto) {
        return userRepository.save(UserDto.mapFromDto(userDto));
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDto::mapToDto)
                .toList();
    }

    @Override
    public UserDto getUserById(long id) {
        if (id < 0) throw new IllegalArgumentException("Enter correct value!");
        return UserDto.mapToDto(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id: " + id + "does not exist!")));
    }
}
