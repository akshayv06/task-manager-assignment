package com.taskmanager.task_manager.dto;

import com.taskmanager.task_manager.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private String taskNumber;
    private String title;
    private String description;
    private TaskStatus status;

}
