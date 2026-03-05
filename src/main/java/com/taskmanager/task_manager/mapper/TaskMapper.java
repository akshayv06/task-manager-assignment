package com.taskmanager.task_manager.mapper;

import com.taskmanager.task_manager.dto.TaskRequest;
import com.taskmanager.task_manager.dto.TaskResponse;
import com.taskmanager.task_manager.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskRequest request);

    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "createdAt", source = "createdAt")
    TaskResponse toResponse(Task task);
}