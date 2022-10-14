package com.example.userservice.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TrainingDTO {

    private String adminId;
    private String id;
    private String name;
    private String description;
    private Date createdDate;

}
