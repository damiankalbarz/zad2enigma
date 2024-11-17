package com.example.zad2enigma.mapper;

import com.example.zad2enigma.dto.TaskDto;
import com.example.zad2enigma.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TaskMapper {

    @Mapping(target = "assignedUsers", source = "assignedUsers")
    Task toTask(TaskDto taskDto);

    @Mapping(target = "assignedUsers", source = "assignedUsers")
    TaskDto toTaskDto(Task task);

    List<TaskDto> toTaskDtos(List<Task> tasks);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedUsers", ignore = true)
    void updateTask(@MappingTarget Task target, Task source);


}
