package com.example.userservice.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTaskStatus {
    private boolean status;
    private String url;
    private Date completedDate;
}
