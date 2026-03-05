package com.taskmanager.task_manager.controller;

import com.taskmanager.task_manager.dto.TaskRequest;
import com.taskmanager.task_manager.dto.TaskResponse;
import com.taskmanager.task_manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public TaskResponse createTask(@RequestBody TaskRequest request){

        return taskService.createTask(request);
    }

    @GetMapping
    public Page<TaskResponse> getTasks(Pageable pageable){

        return taskService.getTasks(pageable);
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public void approve(@PathVariable Long id){

        taskService.approveTask(id);
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public void reject(@PathVariable Long id){

        taskService.rejectTask(id);
    }
}