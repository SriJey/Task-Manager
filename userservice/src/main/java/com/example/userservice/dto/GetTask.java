package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTask {

    private String trainingId;
    private String dayName;

}
