package com.example.userservice.service;

import java.util.List;

import com.example.userservice.dto.AddDayandTask;
import com.example.userservice.dto.AddResponse;
import com.example.userservice.dto.DeleteTask;
import com.example.userservice.dto.GetTask;
import com.example.userservice.dto.SingleTask;
import com.example.userservice.dto.TrainingDTO;
import com.example.userservice.dto.TrainingResponse;
import com.example.userservice.dto.TrainingWithNoDTO;
import com.example.userservice.dto.TrainingandTaskDTO;
import com.example.userservice.dto.UpdateTask;

public interface AdminService {
    AddResponse addTraining(TrainingDTO trainingDTO);

    AddResponse addDay(AddDayandTask addDayandTask);

    List<TrainingWithNoDTO> getAllTrainings();

    AddResponse addTasks(SingleTask singleTask);

    TrainingResponse getTraining(String trainingId);

    TrainingandTaskDTO getTasks(GetTask getTask);

    AddResponse updateTask(UpdateTask updateTask);

    AddResponse deleteTask(DeleteTask deleteTask);

    List<String> getUsersOfTraining(String trainingId);

    AddResponse removeUserFromTraining(String trainingId, String userId);

}
