package com.example.userservice.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.userservice.dto.AddDayandTask;
import com.example.userservice.dto.AddResponse;
import com.example.userservice.dto.AddTaskDTO;
import com.example.userservice.dto.DaysDTO;
import com.example.userservice.dto.DeleteTask;
import com.example.userservice.dto.GetTask;
import com.example.userservice.dto.SingleTask;
import com.example.userservice.dto.TaskDTO;
import com.example.userservice.dto.TrainingDTO;
import com.example.userservice.dto.TrainingResponse;
import com.example.userservice.dto.TrainingWithNoDTO;
import com.example.userservice.dto.TrainingandTaskDTO;
import com.example.userservice.dto.UpdateTask;
import com.example.userservice.helper.ValidationClass;
import com.example.userservice.model.Days;
import com.example.userservice.model.Task;
import com.example.userservice.model.Training;
import com.example.userservice.model.UserListForTraining;
import com.example.userservice.model.UserWithTrainingList;
import com.example.userservice.repo.TrainingRepo;
import com.example.userservice.repo.UserListfForTrainingRepo;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.repo.UserWithTrainingListRepo;
import com.example.userservice.service.AdminService;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TrainingRepo trainingRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserListfForTrainingRepo userListfForTrainingRepo;

    @Autowired
    UserWithTrainingListRepo userWithTrainingListRepo;

    @Override
    public AddResponse addTraining(TrainingDTO trainingDTO) {
        try {
            if (ValidationClass.validateTraining(trainingDTO)) {
                Training training = modelMapper.map(trainingDTO, Training.class);
                training.setId(UUID.randomUUID().toString());
                training.setDaysMap(new HashMap<>());
                training.setCreatedDate(new Date());
                trainingRepo.save(training);
                userListfForTrainingRepo.save(new UserListForTraining(training.getId(), new ArrayList<>()));
                return new AddResponse("Ok", "Created");
            }
        } catch (Exception exception) {
            return new AddResponse("Error", exception.getMessage());
        }
        return new AddResponse();
    }

    @Override
    public AddResponse addDay(AddDayandTask addDayandTask) {
        try {
            Optional<Training> training = trainingRepo.findById(addDayandTask.getTrainingId());
            if (!training.isPresent())
                return new AddResponse("Error", "Training Not Found..");

            Days days = training.get().getDaysMap().get(addDayandTask.getName());
            if (days != null) {
                for (AddTaskDTO addTaskDTO : addDayandTask.getTasks()) {
                    Task task = modelMapper.map(addTaskDTO, Task.class);
                    task.setId(UUID.randomUUID().toString());
                    training.get().getDaysMap().get(addDayandTask.getName()).getTasks().put(task.getId(), task);
                }
                trainingRepo.save(training.get());
                return new AddResponse("Ok", "Updated");
            }
            Days day = modelMapper.map(addDayandTask, Days.class);
            day.setTasks(new TreeMap<>());
            for (AddTaskDTO addTaskDTO : addDayandTask.getTasks()) {

                Task task = modelMapper.map(addTaskDTO, Task.class);
                task.setId(UUID.randomUUID().toString());
                day.getTasks().put(task.getId(), task);
            }
            training.get().getDaysMap().put(day.getName(), day);
            trainingRepo.save(training.get());
            return new AddResponse("Ok", "Added");
        } catch (Exception exception) {
            return new AddResponse("Error", exception.getMessage());
        }
    }

    @Override
    public List<TrainingWithNoDTO> getAllTrainings() {
        try {
            List<Training> trainings = trainingRepo.findAll();
            if (trainings.isEmpty())
                throw new Exception("No training available.");
            List<TrainingWithNoDTO> trainingWithNoDTOs = new ArrayList<>();
            int noOfTasks = 0;
            int noOfDays = 0;
            for (Training training : trainings) {
                TrainingWithNoDTO trainingWithNoDTO = modelMapper.map(training, TrainingWithNoDTO.class);
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
    public AddResponse addTasks(SingleTask singleTask) {

        try {
            Optional<Training> training = trainingRepo.findById(singleTask.getTrainingId());
            Task task = modelMapper.map(singleTask, Task.class);
            task.setId(UUID.randomUUID().toString());
            training.get().getDaysMap().get(singleTask.getDay()).getTasks().put(singleTask.getName(), task);
            trainingRepo.save(training.get());
            return new AddResponse("Ok", "Added successfully");
        } catch (Exception e) {
            return new AddResponse("Ok", e.getMessage());
        }

    }

    @Override
    public TrainingResponse getTraining(String trainingId) {

        Optional<Training> result = trainingRepo.findById(trainingId);
        if (!result.isPresent())
            return null;
        TrainingResponse trainingResponse = new TrainingResponse();
        Training training = result.get();
        trainingResponse.setId(training.getId());
        trainingResponse.setName(training.getName());
        trainingResponse.setDescription(training.getDescription());
        trainingResponse.setCreatedDate(training.getCreatedDate());

        List<DaysDTO> days = new ArrayList<>();
        for (Map.Entry<String, Days> day : training.getDaysMap().entrySet()) {
            DaysDTO daysDTO = new DaysDTO();
            Days d = day.getValue();
            daysDTO.setName(d.getName());
            // daysDTO.setTrainingId(d.getTrainingId());
            daysDTO.setTasks(
                    d.getTasks().values()
                            .stream()
                            .map(this::mapTask)
                            .collect(Collectors.toList()));
            days.add(daysDTO);
        }

        trainingResponse.setDays(days);
        return trainingResponse;
    }

    @Override
    public TrainingandTaskDTO getTasks(GetTask getTask) {

        try {
            Optional<Training> training = trainingRepo.findById(getTask.getTrainingId());
            Days day = new Days();
            day.setTasks(training.get().getDaysMap().get(getTask.getDayName()).getTasks());
            List<AddTaskDTO> addTaskDTOs = new ArrayList<>();
            for (String key : day.getTasks().keySet()) {
                addTaskDTOs.add(modelMapper.map(day.getTasks().get(key), AddTaskDTO.class));
            }
            TrainingandTaskDTO trainingandTaskDTO = modelMapper.map(training.get(), TrainingandTaskDTO.class);
            trainingandTaskDTO.setTasks(addTaskDTOs);
            return trainingandTaskDTO;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public AddResponse updateTask(UpdateTask updateTask) {
        Optional<Training> training = trainingRepo.findById(updateTask.getTrainingId());
        Task task = modelMapper.map(updateTask, Task.class);
        training.get().getDaysMap().get(updateTask.getDayName()).getTasks().put(updateTask.getId(), task);
        trainingRepo.save(training.get());
        return new AddResponse("OK", "success");
    }

    @Override
    public AddResponse deleteTask(DeleteTask deleteTask) {
        Optional<Training> training = trainingRepo.findById(deleteTask.getTrainingId());
        training.get().getDaysMap().get(deleteTask.getDayName()).getTasks().remove(deleteTask.getTaskId());
        trainingRepo.save(training.get());
        return new AddResponse("OK", "success");
    }

    @Override
    public List<String> getUsersOfTraining(String trainingId) {
        return userListfForTrainingRepo.findById(trainingId).get().getUserIds();
    }

    @Override
    public AddResponse removeUserFromTraining(String trainingId, String userId) {

        try {

            userRepo.deleteByUserIdAndTrainingId(userId, trainingId);// removing in user DB
            // removing in training
            Optional<UserListForTraining> userListForTraining = userListfForTrainingRepo.findById(trainingId);
            userListForTraining.get().getUserIds().remove(userId);
            userListfForTrainingRepo.save(userListForTraining.get());
            // removing in user training list
            Optional<UserWithTrainingList> userWithTrainingList = userWithTrainingListRepo.findById(userId);
            userWithTrainingList.get().getTrainingIds().remove(trainingId);
            userWithTrainingListRepo.save(userWithTrainingList.get());
            return new AddResponse("OK", "Removed Successfully");
        } catch (Exception exception) {
            return new AddResponse("ERROR", exception.getMessage());
        }
    }

    private TaskDTO mapTask(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }
}
