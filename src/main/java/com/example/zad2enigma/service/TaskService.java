package com.example.zad2enigma.service;

import com.example.zad2enigma.dto.TaskDto;
import com.example.zad2enigma.entity.Task;
import com.example.zad2enigma.entity.TaskStatus;
import com.example.zad2enigma.entity.User;
import com.example.zad2enigma.mapper.TaskMapper;
import com.example.zad2enigma.repository.TaskRepository;
import com.example.zad2enigma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskMapper taskMapper;

    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return taskMapper.toTaskDtos(tasks);
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return taskMapper.toTaskDto(task);
    }

    public List<TaskDto> getTasksByStatus(TaskStatus status) {
        List<Task> tasks = taskRepository.findByStatus(status);
        if (tasks.isEmpty()) {
            throw new RuntimeException("No tasks found");
        }
        return taskMapper.toTaskDtos(tasks);
    }


    public TaskDto createTask(TaskDto taskDto) {
        Task task = taskMapper.toTask(taskDto);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskDto(savedTask);
    }


    public TaskDto assignUsersToTask(Long taskId, List<Long> userIds) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found" ));
        List<User> users = userRepository.findAllById(userIds);
        task.getAssignedUsers().addAll(users);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toTaskDto(updatedTask);
    }

    public TaskDto removeUserFromTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.getAssignedUsers().remove(user);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toTaskDto(updatedTask);
    }

    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskMapper.updateTask(task, taskMapper.toTask(taskDto));
        Task savedTask = taskRepository.save(task);

        return taskMapper.toTaskDto(savedTask);
    }

    public TaskDto patchTask(Long id, TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (taskDto.getTitle() != null) {
            task.setTitle(taskDto.getTitle());
        }

        if (taskDto.getDescription() != null) {
            task.setDescription(taskDto.getDescription());
        }

        if (taskDto.getDueDate() != null) {
            task.setDueDate(taskDto.getDueDate());
        }

        if (taskDto.getStatus() != null) {
            task.setStatus(taskDto.getStatus());
        }

        Task savedTask = taskRepository.save(task);

        return taskMapper.toTaskDto(savedTask);
    }


    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
