package com.example.userservice.dto;

import lombok.Data;

@Data
public class UpdateTaskStatus {

    private String userId;
    private String trainingId;
    private String dayName;
    private String taskId;
}
