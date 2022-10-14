package com.example.userservice.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.userservice.model.Training;

@Repository
public interface TrainingRepo extends MongoRepository<Training, String> {

    Training findAllByName(String name);

    List<Training> findAllByIdIn(List<String> trainingIds);

}
