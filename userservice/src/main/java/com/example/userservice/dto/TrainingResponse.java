package com.example.userservice.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class TrainingResponse {

    private String id;
    private String name;
    private String description;
    private Date createdDate;
    private List<DaysDTO> days;

}
