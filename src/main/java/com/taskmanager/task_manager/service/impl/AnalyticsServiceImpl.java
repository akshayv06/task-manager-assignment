package com.taskmanager.task_manager.service.impl;

import com.taskmanager.task_manager.entity.Task;
import com.taskmanager.task_manager.repository.TaskRepository;
import com.taskmanager.task_manager.service.AnalyticsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final TaskRepository taskRepository;

    public AnalyticsServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Map<String, Long> getTasksByStatus() {

        List<Task> tasks = taskRepository.findAll();

        Map<String, Long> result = new HashMap<>();

        for (Task task : tasks) {
            String status = task.getStatus().name();
            result.put(status, result.getOrDefault(status, 0L) + 1);
        }

        return result;
    }

    @Override
    public Map<String, Long> getDailyTaskCount() {

        List<Task> tasks = taskRepository.findAll();

        Map<String, Long> result = new HashMap<>();

        for (Task task : tasks) {

            LocalDate date = task.getCreatedAt().toLocalDate();

            result.put(date.toString(),
                    result.getOrDefault(date.toString(), 0L) + 1);
        }

        return result;
    }
}