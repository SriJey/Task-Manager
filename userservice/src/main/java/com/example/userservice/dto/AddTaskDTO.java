package com.example.userservice.dto;

import lombok.Data;

@Data
public class AddTaskDTO {
    private String id;
    private String name;
    private String description;
    private String type;
}
