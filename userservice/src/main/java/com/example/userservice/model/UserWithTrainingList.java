package com.example.userservice.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Document
@AllArgsConstructor
public class UserWithTrainingList {
    @Id
    private String id;
    private List<String> trainingIds;
}
