package com.example.userservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class TrainingandTaskDTO {

    private String name;
    private String description;
    private List<AddTaskDTO> tasks;

}
