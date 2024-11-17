package com.example.zad2enigma.service;

import com.example.zad2enigma.dto.UserDto;
import com.example.zad2enigma.entity.User;
import com.example.zad2enigma.mapper.UserMapper;
import com.example.zad2enigma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserDtos(users);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toUserDto(user);
    }

    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
