package com.project.UserTaskApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT , reason = "Task already exists")
// Use the@ResponseStatus annotation to set the exception message and status
public class TaskAlreadyExistsException extends Exception {
}
