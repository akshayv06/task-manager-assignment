package com.taskmanager.task_manager.entity;

import com.taskmanager.task_manager.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
//task-manager
//│
//        ├── config
//│     ├── SecurityConfig
//│     ├── JwtFilter
//│     ├── SwaggerConfig
//│
//        ├── controller
//│     ├── AuthController
//│     ├── TaskController
//│     ├── AnalyticsController
//│
//        ├── service
//│     ├── AuthService
//│     ├── TaskService
//│     ├── AnalyticsService
//│
//        ├── service.impl
//│     ├── AuthServiceImpl
//│     ├── TaskServiceImpl
//│
//        ├── repository
//│     ├── UserRepository
//│     ├── TaskRepository
//│
//        ├── entity
//│     ├── User
//│     ├── Task
//│
//        ├── dto
//│     ├── AuthRequest
//│     ├── AuthResponse
//│     ├── TaskRequest
//│     ├── TaskResponse
//│
//        ├── mapper
//│     ├── TaskMapper
//│
//        ├── security
//│     ├── JwtUtil
//│     ├── CustomUserDetailsService
//│
//        ├── exception
//│     ├── GlobalExceptionHandler
//│     ├── ResourceNotFoundException
//│
//        ├── audit
//│     ├── AuditListener
//│
//        └── TaskManagerApplication