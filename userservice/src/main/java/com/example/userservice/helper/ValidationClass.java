package com.example.userservice.helper;

import com.example.userservice.dto.TrainingDTO;

public class ValidationClass {

    public static boolean validateTraining(TrainingDTO trainingDTO) throws Exception {

        if ((trainingDTO.getAdminId()).trim().isEmpty())
            throw new Exception("admin id can't be empty.");
        if ((trainingDTO.getDescription()).trim().isEmpty())
            throw new Exception("description can't be empty.");
        if ((trainingDTO.getName()).trim().isEmpty())
            throw new Exception("Name can't be empty.");
        return true;

    }

}
