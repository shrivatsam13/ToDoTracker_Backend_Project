package com.project.UserAuthenticationApp.controller;

import com.project.UserAuthenticationApp.exception.UserAlreadyExistsException;
import com.project.UserAuthenticationApp.exception.InvalidCredentialsException;
import com.project.UserAuthenticationApp.exception.UserNotFoundException;
import com.project.UserAuthenticationApp.security.SecurityTokenGenerator;
import com.project.UserAuthenticationApp.service.IUserService;
import com.project.UserAuthenticationApp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
//@CrossOrigin("http://localhost:4200/")
public class UserController {
    //Autowire the dependencies for UserService and SecurityTokenGenerator
    private final IUserService iUserService;
    private SecurityTokenGenerator securityTokenGenerator;
    @Autowired
    public UserController(IUserService iUserService , SecurityTokenGenerator securityTokenGenerator) {
        this.iUserService = iUserService;
        this.securityTokenGenerator = securityTokenGenerator;
    }


    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws UserAlreadyExistsException {
        // Write the logic to save a user,
        // return 201 status if user is saved else 500 status
        try{
        return new ResponseEntity<>(iUserService.saveUser(user),HttpStatus.CREATED);
            }
        catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody User user) throws UserNotFoundException {
        try {
            iUserService.deleteUser(user);
            return new ResponseEntity<>("User deleted successfully from mysql", HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User doesn't exist", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws InvalidCredentialsException {
        // Generate the token on login,
        // return 200 status if user is saved else 500 status
        User retrievedUser = iUserService.getUserByUserEmailAndPassword(user.getUserEmail(),user.getPassword());
        if(retrievedUser==null)
        {
            throw new InvalidCredentialsException();
        }
        String token = securityTokenGenerator.createToken(user);
        return new ResponseEntity<>(token,HttpStatus.OK);
    }
}
