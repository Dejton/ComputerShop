package com.computershop.service;

import com.computershop.model.entity.User;
import com.computershop.model.dto.UserDto;

import java.util.List;

public interface UserService {
    User addUser(UserDto userDto);
    void deleteUserById(long id);
    List<UserDto> getAllUsers();
    UserDto getUserById(long id);

    User getUserByLogin(String login);
}
