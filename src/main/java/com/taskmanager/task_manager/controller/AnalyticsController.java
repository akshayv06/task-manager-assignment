package com.taskmanager.task_manager.controller;

import com.taskmanager.task_manager.service.AnalyticsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/tasks-by-status")
    public Map<String, Long> tasksByStatus() {
        return analyticsService.getTasksByStatus();
    }

    @GetMapping("/daily-tasks")
    public Map<String, Long> dailyTasks() {
        return analyticsService.getDailyTaskCount();
    }
}