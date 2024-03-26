package com.project.UserTaskApp.repository;

import com.project.UserTaskApp.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserTaskRepository extends MongoRepository<User, String> {
    User findUserByUserEmail(String userEmail);
}
