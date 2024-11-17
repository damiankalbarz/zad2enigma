package com.example.zad2enigma.controller;

import com.example.zad2enigma.dto.UserDto;
import com.example.zad2enigma.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto(
                1L,
                "John",
                "Doe",
                "john.doe@example.com"
        );
    }

    @Test
    void getAllUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(userDto));

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userDto.getId()))
                .andExpect(jsonPath("$[0].firstName").value(userDto.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(userDto.getLastName()))
                .andExpect(jsonPath("$[0].email").value(userDto.getEmail()));
    }

    @Test
    void getUserById() throws Exception {
        Mockito.when(userService.getUserById(1L)).thenReturn(userDto);

        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.firstName").value(userDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDto.getLastName()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    @Test
    void createUser() throws Exception {
        Mockito.when(userService.createUser(any(UserDto.class))).thenReturn(userDto);

        String userJson = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com"
                }
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.firstName").value(userDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userDto.getLastName()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void returnBadRequestForInvalidUserJson() throws Exception {
        String invalidJson = """
        {
            "firstName": "John",
            "email": "invalidEmail"
        }
        """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
