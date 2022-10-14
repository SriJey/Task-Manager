package com.example.userservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.userservice.model.UserListForTraining;

public interface UserListfForTrainingRepo extends MongoRepository<UserListForTraining, String> {

}
