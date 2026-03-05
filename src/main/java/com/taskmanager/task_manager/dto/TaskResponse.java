package com.taskmanager.task_manager.dto;

import com.taskmanager.task_manager.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private String taskNumber;
    private String title;
    private String description;
    private TaskStatus status;
    private String createdBy;
    private LocalDateTime createdAt;

}
