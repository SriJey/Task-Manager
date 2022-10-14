package com.example.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.userservice.dto.AddResponse;
import com.example.userservice.dto.UpdateTaskStatus;
import com.example.userservice.dto.TrainingWithNoDTO;
import com.example.userservice.model.User;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.service.UserService;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;

    @PostMapping("/assign/{userId}/{trainingId}")
    public User assignTraining(@PathVariable String userId, @PathVariable String trainingId) {
        try {
            return userService.assignTraining(userId, trainingId);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
        }
    }

    @GetMapping("/user-training/{userId}")
    public List<TrainingWithNoDTO> getUserTraining(@PathVariable String userId) {
        try {
            return userService.getUserTraining(userId);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PutMapping("/update/task/status")
    public AddResponse updateTaskStatus(@RequestBody UpdateTaskStatus updateTaskStatus) {
        return userService.updateUserTaskStatus(updateTaskStatus);
    }

}
