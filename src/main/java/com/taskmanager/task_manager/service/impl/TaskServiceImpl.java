package com.taskmanager.task_manager.service.impl;


import com.taskmanager.task_manager.dto.TaskRequest;
import com.taskmanager.task_manager.dto.TaskResponse;
import com.taskmanager.task_manager.entity.Task;
import com.taskmanager.task_manager.enums.TaskStatus;
import com.taskmanager.task_manager.mapper.TaskMapper;
import com.taskmanager.task_manager.repository.TaskRepository;
import com.taskmanager.task_manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper mapper;

    @Override
    public TaskResponse createTask(TaskRequest request) {

        Task task = mapper.toEntity(request);

        task.setStatus(TaskStatus.CREATED);
        task.setTaskNumber("TASK-" + UUID.randomUUID().toString().substring(0,5));

        taskRepository.save(task);

        return mapper.toResponse(task);
    }

    @Override
    public Page<TaskResponse> getTasks(Pageable pageable) {

        return taskRepository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public void approveTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if(task.getStatus()!= TaskStatus.CREATED)
            throw new RuntimeException("Task already processed");

        task.setStatus(TaskStatus.APPROVED);

        taskRepository.save(task);
    }

    @Override
    public void rejectTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if(task.getStatus()!=TaskStatus.CREATED)
            throw new RuntimeException("Task already processed");

        task.setStatus(TaskStatus.REJECTED);

        taskRepository.save(task);
    }
}