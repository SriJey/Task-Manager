package com.example.userservice.model;

import java.util.TreeMap;

import lombok.Data;

@Data
public class Days {

    private String trainingId;
    private String name;
    private TreeMap<String, Task> tasks;

}
