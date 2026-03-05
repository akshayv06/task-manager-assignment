package com.taskmanager.task_manager.mapper;

import com.taskmanager.task_manager.dto.TaskRequest;
import com.taskmanager.task_manager.dto.TaskResponse;
import com.taskmanager.task_manager.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskRequest request);

    TaskResponse toResponse(Task task);
}