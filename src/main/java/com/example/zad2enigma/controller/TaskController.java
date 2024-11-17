package com.example.zad2enigma.controller;

import com.example.zad2enigma.dto.TaskDto;
import com.example.zad2enigma.entity.TaskStatus;
import com.example.zad2enigma.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        TaskDto task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDto>> getTasksByStatus(@PathVariable TaskStatus status) {
        List<TaskDto> tasks = taskService.getTasksByStatus(status);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto) {
        TaskDto createdTask = taskService.createTask(taskDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}/users")
    public ResponseEntity<TaskDto> assignUsersToTask(
            @PathVariable Long taskId, @Valid @RequestBody List<Long> userIds) {
        TaskDto updatedTask = taskService.assignUsersToTask(taskId, userIds);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}/users/{userId}")
    public ResponseEntity<TaskDto> removeUserFromTask(
            @PathVariable Long taskId, @PathVariable Long userId) {
        TaskDto updatedTask = taskService.removeUserFromTask(taskId, userId);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable Long id, @RequestBody TaskDto taskDto) {
        TaskDto updatedTask = taskService.updateTask(id, taskDto);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDto> patchTask(
            @PathVariable Long id, @RequestBody TaskDto taskDto) {
        TaskDto patchedTask = taskService.patchTask(id, taskDto);
        return new ResponseEntity<>(patchedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
