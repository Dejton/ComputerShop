package com.computershop.service.impl;

import com.computershop.model.entity.User;
import com.computershop.model.dto.UserDto;
import com.computershop.repository.UserRepository;
import com.computershop.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

//    @Override
//    public String encodePassword(String password) {
//        return bCryptPasswordEncoder.encode(password);
//    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login).orElse(null);
    }
}
