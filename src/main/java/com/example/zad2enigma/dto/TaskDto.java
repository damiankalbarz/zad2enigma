package com.example.zad2enigma.dto;

import com.example.zad2enigma.entity.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Status is mandatory")
    private TaskStatus status;

    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;

    private List<UserDto> assignedUsers;
}
