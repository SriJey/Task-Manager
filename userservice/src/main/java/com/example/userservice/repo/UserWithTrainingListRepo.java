package com.example.userservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.userservice.model.UserWithTrainingList;

public interface UserWithTrainingListRepo extends MongoRepository<UserWithTrainingList, String> {

}
