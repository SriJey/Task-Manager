package com.example.userservice.model;

import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDay {
    private TreeMap<String, UserTask> userTasksMap;

}
