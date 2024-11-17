package com.example.zad2enigma.repository;

import com.example.zad2enigma.entity.Task;
import com.example.zad2enigma.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
}
