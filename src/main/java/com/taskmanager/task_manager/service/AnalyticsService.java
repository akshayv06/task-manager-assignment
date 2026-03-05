package com.taskmanager.task_manager.service;

import java.util.Map;

public interface AnalyticsService {

    Map<String, Long> getTasksByStatus();

    Map<String, Long> getDailyTaskCount();
}