package com.example.userservice.service;

import java.util.List;

import com.example.userservice.dto.AddResponse;
import com.example.userservice.dto.UpdateTaskStatus;
import com.example.userservice.model.User;
import com.example.userservice.dto.TrainingWithNoDTO;

public interface UserService {
    User assignTraining(String userId, String trainingId) throws Exception;

    AddResponse updateUserTaskStatus(UpdateTaskStatus updateTaskStatus);

    List<TrainingWithNoDTO> getUserTraining(String userId) throws Exception;
}
