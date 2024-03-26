package com.project.UserTaskApp.service;

import com.project.UserTaskApp.domain.Task;
import com.project.UserTaskApp.domain.User;
import com.project.UserTaskApp.exceptions.TaskAlreadyExistsException;
import com.project.UserTaskApp.exceptions.TaskNotFoundException;
import com.project.UserTaskApp.exceptions.UserAlreadyExistsException;
import com.project.UserTaskApp.exceptions.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    User registerUser(User user) throws UserAlreadyExistsException;

    String removeUser(User user) throws UserNotFoundException;

    List<User> getAllUsers();
    void archiveTasksIfDueDatePassed(User user);

    List<Task> saveTaskToTaskList(Task task, String userEmail) throws TaskAlreadyExistsException, UserNotFoundException;

    List<Task> getAllTasksFromTaskList(String userEmail) throws Exception;

    User deleteTaskFromTaskList(String userEmail, UUID taskId) throws TaskNotFoundException, UserNotFoundException;

    User saveTaskToArchievedTaskList(Task task, String userEmail) throws TaskAlreadyExistsException, UserNotFoundException, TaskNotFoundException;

    List<Task> getAllTasksFromArchievedTaskList(String userEmail) throws Exception;

    User deleteTaskFromArchievedTaskList(String userEmail, UUID taskId) throws TaskNotFoundException, UserNotFoundException;

    User moveTaskFromArchiveToTaskList(String userEmail, Task task) throws TaskNotFoundException, UserNotFoundException, TaskAlreadyExistsException;

    List<Task> updateUserTaskListWithGivenTask(String userEmail,Task task) throws UserNotFoundException, TaskNotFoundException, TaskAlreadyExistsException;

    List<Task> updateArchivedTaskListWithGivenTask(String userId, Task task) throws UserNotFoundException, TaskNotFoundException, TaskAlreadyExistsException;

    Task markTaskAsCompleted(String userId,  UUID taskId) throws UserNotFoundException, TaskNotFoundException;
}
