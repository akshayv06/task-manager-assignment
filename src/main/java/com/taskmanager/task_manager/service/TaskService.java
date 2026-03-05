package com.taskmanager.task_manager.service;

import com.taskmanager.task_manager.dto.TaskRequest;
import com.taskmanager.task_manager.dto.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    TaskResponse createTask(TaskRequest request);

    Page<TaskResponse> getTasks(Pageable pageable);

    void approveTask(Long id);

    void rejectTask(Long id);
}