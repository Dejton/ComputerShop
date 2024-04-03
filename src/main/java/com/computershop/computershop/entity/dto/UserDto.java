package com.computershop.computershop.entity.dto;

import com.computershop.computershop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {
    private long id;
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String address;
    private String role;

    public static UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
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
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .role(userDto.getRole())
                .build();
    }

}
