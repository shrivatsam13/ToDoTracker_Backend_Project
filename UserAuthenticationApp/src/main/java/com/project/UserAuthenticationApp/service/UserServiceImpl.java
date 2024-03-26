package com.project.UserAuthenticationApp.service;

import com.project.UserAuthenticationApp.domain.User;
import com.project.UserAuthenticationApp.exception.UserAlreadyExistsException;
import com.project.UserAuthenticationApp.exception.InvalidCredentialsException;
import com.project.UserAuthenticationApp.exception.UserNotFoundException;
import com.project.UserAuthenticationApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

// Autowire the UserRepository using constructor autowiring
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        //save the user in the db
        if(userRepository.findById(user.getUserEmail()).isPresent())
        {
            throw new UserAlreadyExistsException();
        }
        System.out.println(user);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) throws UserNotFoundException {
        if(!userRepository.findById(user.getUserEmail()).isPresent())
        {
            throw new UserNotFoundException();
        }
        userRepository.delete(user);
    }

    @Override
    public User getUserByUserEmailAndPassword(String userEmail, String password) throws InvalidCredentialsException {
      // Validate for wrong credentials
        System.out.println("userEmail"+userEmail);
        System.out.println("password"+password);
        User loggedInUser = userRepository.findByUserEmailAndPassword(userEmail , password);
        System.out.println(loggedInUser);
        if(loggedInUser == null)
        {
            throw new InvalidCredentialsException();
        }

        return loggedInUser;
    }

}
