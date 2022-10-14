package com.example.userservice.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.userservice.model.User;

@Repository
public interface UserRepo extends MongoRepository<User, String> {

    User findByUserIdAndTrainingId(String id, String trainingId);

    List<User> findAllByUserId(String userId);

    void deleteByUserIdAndTrainingId(String userId, String trainingId);

}
