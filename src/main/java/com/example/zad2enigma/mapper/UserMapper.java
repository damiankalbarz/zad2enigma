package com.example.zad2enigma.mapper;

import com.example.zad2enigma.dto.UserDto;
import com.example.zad2enigma.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

    List<UserDto> toUserDtos(List<User> users);

    List<User> toUsers(List<UserDto> userDtos);
}
