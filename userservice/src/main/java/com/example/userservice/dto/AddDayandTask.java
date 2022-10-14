package com.example.userservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class AddDayandTask {

    private String name;
    private String trainingId;
    private List<AddTaskDTO> tasks;

}
