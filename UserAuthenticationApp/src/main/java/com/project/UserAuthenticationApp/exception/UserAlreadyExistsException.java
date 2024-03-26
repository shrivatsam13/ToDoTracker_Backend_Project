package com.project.UserAuthenticationApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason = "User already exists")
// Use the@ResponseStatus annotation to set the exception message and status
public class UserAlreadyExistsException extends  Exception{
}
