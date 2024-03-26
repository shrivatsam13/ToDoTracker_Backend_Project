package com.project.UserAuthenticationApp.service;

import com.project.UserAuthenticationApp.domain.User;
import com.project.UserAuthenticationApp.exception.UserAlreadyExistsException;
import com.project.UserAuthenticationApp.exception.InvalidCredentialsException;
import com.project.UserAuthenticationApp.exception.UserNotFoundException;

public interface IUserService {
    User saveUser(User user) throws UserAlreadyExistsException;
    void deleteUser(User user) throws UserNotFoundException;
    User getUserByUserEmailAndPassword(String userEmail, String password) throws InvalidCredentialsException;
}
