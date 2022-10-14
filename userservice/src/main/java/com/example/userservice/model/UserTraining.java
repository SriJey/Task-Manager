package com.example.userservice.model;

import java.util.HashMap;

import lombok.Data;

@Data
public class UserTraining {
    private HashMap<String, UserDay> daysMap;
}
