package com.example.userservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class DaysDTO {
    // private String trainingId;
    private String name;
    private List<TaskDTO> tasks;
}