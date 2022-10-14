package com.example.mailService.model;

import java.util.Date;

import lombok.Data;

@Data
public class TaskStatus {
    private String taskQuestion;
    private String status;
    private Date completed;
    private Date deadline;
}
