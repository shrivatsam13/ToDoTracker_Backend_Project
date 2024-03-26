package com.project.UserAuthenticationApp.repository;

import com.project.UserAuthenticationApp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserEmailAndPassword(String userEmail, String password);
}
