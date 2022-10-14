package com.example.userservice.model;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTask {
    private HashMap<String, UserTaskStatus> userTaskStatusMap;
}
