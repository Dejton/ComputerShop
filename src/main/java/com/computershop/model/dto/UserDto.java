package com.computershop.model.dto;

import com.computershop.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@AllArgsConstructor
@Data
@Builder
public class UserDto {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String login;
    private final String password;
    private final String email;
    private final String address;
    private final String role;

    public static UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .password(user.getPassword())
                .email(user.getEmail())
                .address(user.getAddress())
                .role(user.getRole())
                .build();
        }
    public static User mapFromDto(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .role(userDto.getRole())
                .build();
    }

}
