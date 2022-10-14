package com.example.userservice.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import java.lang.reflect.*;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userservice.dto.AddResponse;
import com.example.userservice.dto.TrainingWithNoDTO;
import com.example.userservice.dto.UpdateTaskStatus;
import com.example.userservice.dto.UserTrainingList;
import com.example.userservice.model.Training;
import com.example.userservice.model.User;
import com.example.userservice.model.UserDay;
import com.example.userservice.model.UserListForTraining;
import com.example.userservice.model.UserTask;
import com.example.userservice.model.UserTaskStatus;
import com.example.userservice.model.UserWithTrainingList;
import com.example.userservice.repo.TrainingRepo;
import com.example.userservice.repo.UserListfForTrainingRepo;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.repo.UserWithTrainingListRepo;
import com.example.userservice.service.UserService;

import io.swagger.annotations.Authorization;

import org.modelmapper.TypeToken;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    TrainingRepo trainingRepo;

    @Autowired
    UserListfForTrainingRepo userListfForTrainingRepo;

    @Autowired
    UserWithTrainingListRepo userWithTrainingListRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public User assignTraining(String userId, String trainingId) throws Exception {

        User userTemp = userRepo.findByUserIdAndTrainingId(userId, trainingId);
        if (userTemp != null)
            throw new Exception("Already assigned..");

        // try {
        Optional<Training> training = trainingRepo.findById(trainingId);
        if (training.isPresent()) {
            TreeMap<String, UserTask> userDayMap = new TreeMap<>();
            for (String dayKey : training.get().getDaysMap().keySet()) {
                HashMap<String, UserTaskStatus> userTaskMap = new HashMap<>();
                for (String taskKey : training.get().getDaysMap().get(dayKey).getTasks().keySet()) {
                    userTaskMap.put(taskKey, new UserTaskStatus(false, "", new Date()));
                }
                UserTask userTask = new UserTask(userTaskMap);
                userDayMap.put(dayKey, userTask);
            }
            UserDay userDay = new UserDay(userDayMap);
            User user = new User(UUID.randomUUID().toString(), userId, trainingId, userDay);
            userRepo.save(user);
            // saving user in training list
            Optional<UserListForTraining> userListForTraining = userListfForTrainingRepo.findById(trainingId);
            userListForTraining.get().getUserIds().add(userId);
            userListfForTrainingRepo.save(userListForTraining.get());
            // saving trainingid in user
            Optional<UserWithTrainingList> userWithTrainingLists = userWithTrainingListRepo.findById(userId);
            if (userWithTrainingLists.isPresent()) {
                userWithTrainingLists.get().getTrainingIds().add(trainingId);
                userWithTrainingListRepo.save(userWithTrainingLists.get());
            } else {
                List<String> trainingList = new ArrayList<>();
                trainingList.add(trainingId);
                userWithTrainingListRepo.save(new UserWithTrainingList(userId, trainingList));
            }
            return user;
        } else {
            throw new Exception("Training not found");
        }

    }

    @Override
    public List<TrainingWithNoDTO> getUserTraining(String userId) throws Exception {

        List<User> userList = userRepo.findAllByUserId(userId);
        if (userList.isEmpty())
            throw new Exception("No trainings assigned..");
        List<String> trainingIds = new ArrayList<>();
        for (User user : userList)
            trainingIds.add(user.getTrainingId());

        try {
            List<Training> trainings = trainingRepo.findAllByIdIn(trainingIds);
            if (trainings.isEmpty())
                throw new Exception("No training available.");
            List<TrainingWithNoDTO> trainingWithNoDTOs = new ArrayList<>();
            int noOfTasks = 0;
            int noOfDays = 0;
            for (Training training : trainings) {
                TrainingWithNoDTO trainingWithNoDTO = modelMapper.map(training,
                        TrainingWithNoDTO.class);
                noOfDays = training.getDaysMap().size();
                for (String dayskey : training.getDaysMap().keySet()) {
                    noOfTasks += training.getDaysMap().get(dayskey).getTasks().size();
                }
                trainingWithNoDTO.setNoOfDays(noOfDays);
                trainingWithNoDTO.setNoOfTasks(noOfTasks);
                trainingWithNoDTOs.add(trainingWithNoDTO);
                noOfTasks = 0;
                noOfDays = 0;
            }
            return trainingWithNoDTOs;
        } catch (Exception exception) {
            return new ArrayList<>();
        }
    }

    @Override
    public AddResponse updateUserTaskStatus(UpdateTaskStatus updateTaskStatus) {
        try {
            User user = userRepo.findByUserIdAndTrainingId(updateTaskStatus.getUserId(),
                    updateTaskStatus.getTrainingId());
            user.getUserDay().getUserTasksMap().get(updateTaskStatus.getDayName()).getUserTaskStatusMap()
                    .get(updateTaskStatus.getTaskId()).setStatus(true);
            System.out.println("status");
            user.getUserDay().getUserTasksMap().get(updateTaskStatus.getDayName()).getUserTaskStatusMap()
                    .get(updateTaskStatus.getTaskId()).setCompletedDate(new Date());
            System.out.println("date");
            userRepo.save(user);
            return new AddResponse("OK", "Updated");
        } catch (Exception exception) {
            return new AddResponse("ERROR", exception.getMessage());
        }
    }
}
