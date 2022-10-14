package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTask {
    private String trainingId;
    private String dayName;
    private String id;
    private String name;
    private String description;
    private String type;
}
