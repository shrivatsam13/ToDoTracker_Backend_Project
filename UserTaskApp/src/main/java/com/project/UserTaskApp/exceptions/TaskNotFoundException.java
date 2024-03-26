package com.project.UserTaskApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND , reason = "Task Not Found")
// Use the@ResponseStatus annotation to set the exception message and status
public class TaskNotFoundException extends Exception {
}
