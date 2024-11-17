package com.example.zad2enigma.controller;

import com.example.zad2enigma.dto.TaskDto;
import com.example.zad2enigma.dto.UserDto;
import com.example.zad2enigma.entity.TaskStatus;
import com.example.zad2enigma.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private TaskDto taskDto;

    @BeforeEach
    void setUp() {
        taskDto = new TaskDto(
                1L,
                "Test Task",
                "Test Description",
                TaskStatus.IN_PROGRESS,
                LocalDate.now().plusDays(1),
                List.of(new UserDto(1L, "John", "Doe", "john.doe@example.com"))
        );
    }


    @Test
    void getAllTasks() throws Exception {
        List<TaskDto> tasks = Arrays.asList(taskDto);
        Mockito.when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(taskDto.getId()))
                .andExpect(jsonPath("$[0].title").value(taskDto.getTitle()))
                .andExpect(jsonPath("$[0].description").value(taskDto.getDescription()))
                .andExpect(jsonPath("$[0].status").value(taskDto.getStatus().toString()))
                .andExpect(jsonPath("$[0].dueDate").value(taskDto.getDueDate().toString()))
                .andExpect(jsonPath("$[0].assignedUsers[0].id").value(taskDto.getAssignedUsers().get(0).getId()))
                .andExpect(jsonPath("$[0].assignedUsers[0].firstName").value(taskDto.getAssignedUsers().get(0).getFirstName()))
                .andExpect(jsonPath("$[0].assignedUsers[0].lastName").value(taskDto.getAssignedUsers().get(0).getLastName()))
                .andExpect(jsonPath("$[0].assignedUsers[0].email").value(taskDto.getAssignedUsers().get(0).getEmail()));
    }

    @Test
    void getTaskById() throws Exception {
        Mockito.when(taskService.getTaskById(1L)).thenReturn(taskDto);

        mockMvc.perform(get("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskDto.getId()))
                .andExpect(jsonPath("$.title").value(taskDto.getTitle()))
                .andExpect(jsonPath("$.description").value(taskDto.getDescription()))
                .andExpect(jsonPath("$.status").value(taskDto.getStatus().toString()))
                .andExpect(jsonPath("$.dueDate").value(taskDto.getDueDate().toString()))
                .andExpect(jsonPath("$.assignedUsers[0].id").value(taskDto.getAssignedUsers().get(0).getId()))
                .andExpect(jsonPath("$.assignedUsers[0].firstName").value(taskDto.getAssignedUsers().get(0).getFirstName()))
                .andExpect(jsonPath("$.assignedUsers[0].lastName").value(taskDto.getAssignedUsers().get(0).getLastName()))
                .andExpect(jsonPath("$.assignedUsers[0].email").value(taskDto.getAssignedUsers().get(0).getEmail()));
    }

    @Test
    void createTask() throws Exception {
        Mockito.when(taskService.createTask(any(TaskDto.class))).thenReturn(taskDto);

        String taskJson = """
                {
                    "title": "Test Task",
                    "description": "Test Description",
                    "status": "IN_PROGRESS",
                    "dueDate": "2024-12-31",
                    "assignedUsers": []
                }
                """;

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(taskDto.getId()))
                .andExpect(jsonPath("$.title").value(taskDto.getTitle()))
                .andExpect(jsonPath("$.description").value(taskDto.getDescription()))
                .andExpect(jsonPath("$.status").value(taskDto.getStatus().toString()))
                .andExpect(jsonPath("$.dueDate").value(taskDto.getDueDate().toString()))
                .andExpect(jsonPath("$.assignedUsers[0].id").value(taskDto.getAssignedUsers().get(0).getId()))
                .andExpect(jsonPath("$.assignedUsers[0].firstName").value(taskDto.getAssignedUsers().get(0).getFirstName()))
                .andExpect(jsonPath("$.assignedUsers[0].lastName").value(taskDto.getAssignedUsers().get(0).getLastName()))
                .andExpect(jsonPath("$.assignedUsers[0].email").value(taskDto.getAssignedUsers().get(0).getEmail()));
    }

    @Test
    void updateTask() throws Exception {
        Mockito.when(taskService.updateTask(eq(1L), any(TaskDto.class))).thenReturn(taskDto);

        String taskJson = """
                {
                    "title": "Updated Task",
                    "description": "Updated Description",
                    "status": "IN_PROGRESS",
                    "dueDate": "2024-12-31",
                    "assignedUsers": []
                }
                """;

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskDto.getId()))
                .andExpect(jsonPath("$.title").value(taskDto.getTitle()))
                .andExpect(jsonPath("$.description").value(taskDto.getDescription()))
                .andExpect(jsonPath("$.status").value(taskDto.getStatus().toString()))
                .andExpect(jsonPath("$.dueDate").value(taskDto.getDueDate().toString()))
                .andExpect(jsonPath("$.assignedUsers[0].id").value(taskDto.getAssignedUsers().get(0).getId()))
                .andExpect(jsonPath("$.assignedUsers[0].firstName").value(taskDto.getAssignedUsers().get(0).getFirstName()))
                .andExpect(jsonPath("$.assignedUsers[0].lastName").value(taskDto.getAssignedUsers().get(0).getLastName()))
                .andExpect(jsonPath("$.assignedUsers[0].email").value(taskDto.getAssignedUsers().get(0).getEmail()));
    }

    @Test
    void patchTask() throws Exception {
        Mockito.when(taskService.patchTask(eq(1L), any(TaskDto.class))).thenReturn(taskDto);

        String patchJson = """
            {
                "title": "Patched Task",
                 "status": "COMPLETED"
            }
            """;

        mockMvc.perform(patch("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskDto.getId()))
                .andExpect(jsonPath("$.title").value(taskDto.getTitle()))
                .andExpect(jsonPath("$.status").value(taskDto.getStatus().toString()));
    }

    @Test
    void returnBadRequestForInvalidJson() throws Exception {
        String invalidJson = """
        {
            "title": "Patched Task",
            "status": 
        }
        """;

        mockMvc.perform(patch("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getTasksByStatus() throws Exception {
        List<TaskDto> tasks = Arrays.asList(taskDto);
        Mockito.when(taskService.getTasksByStatus(TaskStatus.IN_PROGRESS)).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks/status/IN_PROGRESS")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(taskDto.getId()))
                .andExpect(jsonPath("$[0].status").value("IN_PROGRESS"));
    }

    @Test
    void assignUsersToTask() throws Exception {
        Mockito.when(taskService.assignUsersToTask(eq(1L), any(List.class))).thenReturn(taskDto);

        String userIdsJson = "[1, 2, 3]";

        mockMvc.perform(put("/api/tasks/1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userIdsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskDto.getId()));
    }

    @Test
    void removeUserFromTask() throws Exception {
        Mockito.when(taskService.removeUserFromTask(eq(1L), eq(1L))).thenReturn(taskDto);

        mockMvc.perform(delete("/api/tasks/1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskDto.getId()));
    }

}
