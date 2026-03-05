package com.taskmanager.task_manager.service;

import com.taskmanager.task_manager.dto.AuthRequest;
import com.taskmanager.task_manager.dto.AuthResponse;

public interface AuthService {

    AuthResponse login(AuthRequest request);

    String register(AuthRequest request);

}