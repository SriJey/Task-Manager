package com.example.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
import com.example.userservice.service.AdminService;

@RestController
@CrossOrigin("*")
public class TrainingController {

    @Autowired
    AdminService adminService;

    @PostMapping("/training")
    public AddResponse addTraining(@RequestBody TrainingDTO trainingDTO) {
        return adminService.addTraining(trainingDTO);
    }

    @PostMapping("/day")
    public AddResponse addDay(@RequestBody AddDayandTask addDayandTask) {
        return adminService.addDay(addDayandTask);
    }

    @GetMapping("/all-training")
    public List<TrainingWithNoDTO> getTrainings() {
        return adminService.getAllTrainings();
    }

    @PostMapping("/addtask")
    public AddResponse addTask(@RequestBody SingleTask singleTask) {
        return adminService.addTasks(singleTask);
    }

    @GetMapping("/training/{trainingId}")
    public TrainingResponse getTraining(@PathVariable String trainingId) {
        return adminService.getTraining(trainingId);
    }

    @GetMapping("/training/{trainingId}/day/{day}")
    public TrainingandTaskDTO getTask(@PathVariable String trainingId, @PathVariable String day) {
        GetTask getTask = new GetTask(trainingId, day);
        return adminService.getTasks(getTask);
    }

    @PutMapping("/update-task")
    public AddResponse updateTask(@RequestBody UpdateTask updateTask) {
        return adminService.updateTask(updateTask);
    }

    @GetMapping("/getusers/{trainingId}")
    public List<String> getUsersOfTraining(@PathVariable String trainingId) {
        return adminService.getUsersOfTraining(trainingId);
    }

    @PutMapping("/revoke/{userId}/{trainingId}")
    public AddResponse removeUserFromTraining(@PathVariable String trainingId, @PathVariable String userId) {
        return adminService.removeUserFromTraining(trainingId, userId);
    }

    @PutMapping("/delete-task")
    public AddResponse deleteTask(@RequestBody DeleteTask deleteTask) {
        return adminService.deleteTask(deleteTask);
    }
}
