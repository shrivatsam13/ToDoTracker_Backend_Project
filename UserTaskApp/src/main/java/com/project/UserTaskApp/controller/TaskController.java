package com.project.UserTaskApp.controller;

import com.project.UserTaskApp.domain.Task;
import com.project.UserTaskApp.domain.User;
import com.project.UserTaskApp.exceptions.TaskAlreadyExistsException;
import com.project.UserTaskApp.exceptions.TaskNotFoundException;
import com.project.UserTaskApp.exceptions.UserAlreadyExistsException;
import com.project.UserTaskApp.exceptions.UserNotFoundException;
import com.project.UserTaskApp.service.TaskService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v2/")
public class TaskController {
    private ResponseEntity<?> responseEntity;
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Register a new user and save to db, return 201 status if user is saved else 500 status
        try {
            User savedUser = taskService.registerUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed", HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        try {
            String message = taskService.removeUser(user);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User doesn't exist", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUsers() {
        List<User> userList =  taskService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping("user/task")
    public ResponseEntity<?> saveTaskToTaskList(@RequestBody Task task, HttpServletRequest request) throws UserNotFoundException, TaskAlreadyExistsException {
        // add a track to a specific user, return 201 status if track is saved else 500 status
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String userId = claims.getSubject();
            responseEntity = new ResponseEntity<>(taskService.saveTaskToTaskList(task , userId), HttpStatus.CREATED);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @GetMapping("user/tasks")
    public ResponseEntity<?> getAllTasksFromTaskList(HttpServletRequest request) throws Exception {
        // display all the tasks of a specific user, extract user id from claims,
        // return 200 status if user is saved else 500 status
        try{
            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("userId from claims :: " + claims.getSubject());
            String userId = claims.getSubject();
            System.out.println("userId :: "+userId);
            responseEntity = new ResponseEntity<>(taskService.getAllTasksFromTaskList(userId), HttpStatus.OK);
        }catch(UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @DeleteMapping("user/task/{taskId}")
    public ResponseEntity<?> deleteTaskFromTaskList(@PathVariable UUID taskId, HttpServletRequest request) throws TaskNotFoundException {
        // delete a task based on user id and task id, extract user id from claims
        // return 200 status if user is saved else 500 status
        Claims claims = (Claims) request.getAttribute("claims");
        System.out.println("userId from claims :: " + claims.getSubject());
        String userId = claims.getSubject();
        System.out.println("userId :: "+userId);
        try {
            responseEntity = new ResponseEntity<>(taskService.deleteTaskFromTaskList(userId, taskId), HttpStatus.OK);
        } catch (UserNotFoundException | TaskNotFoundException m) {
            throw new TaskNotFoundException();
        }
        return responseEntity;
    }

    @PostMapping("user/archiveTask")
    public ResponseEntity<?> saveTaskToArchivedTaskList(@RequestBody Task task, HttpServletRequest request) throws TaskAlreadyExistsException, UserNotFoundException, TaskNotFoundException {
        // add a task to a specific user archivedTaskList, return 201 status if task is saved else 500 status
        try {
            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("userId from claims :: " + claims.getSubject());
            String userId = claims.getSubject();
            System.out.println("userId :: "+userId);
            responseEntity = new ResponseEntity<>(taskService.saveTaskToArchievedTaskList(task , userId), HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (TaskAlreadyExistsException e) {
            throw new TaskAlreadyExistsException();
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException();
        }
        return responseEntity;
    }

    @PostMapping("user/unarchiveTask")
    public ResponseEntity<?> moveTaskFromArchiveToTaskList(@RequestBody Task task, HttpServletRequest request) throws TaskAlreadyExistsException, UserNotFoundException, TaskNotFoundException {
        // add a task to a specific user archivedTaskList, return 201 status if task is saved else 500 status
        try {
            System.out.println("header" +request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
            System.out.println("userId from claims :: " + claims.getSubject());
            String userId = claims.getSubject();
            System.out.println("userId :: "+userId);
            responseEntity = new ResponseEntity<>(taskService.moveTaskFromArchiveToTaskList(userId, task), HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (TaskAlreadyExistsException e) {
            throw new TaskAlreadyExistsException();
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException();
        }
        return responseEntity;
    }

    @GetMapping("user/archivedTaskList")
    public ResponseEntity<?> getAllTasksFromArchivedTaskList(HttpServletRequest request) throws Exception {
        // display all the task of a specific user from archivedTaskList, extract user id from claims,
        // return 200 status if user is saved else 500 status
        try {
            System.out.println("header" + request.getHeader("Authorization"));
            Claims claims = (Claims) request.getAttribute("claims");
//            System.out.println("userId from claims :: " + claims.getSubject());
            String userId = claims.getSubject();
//            System.out.println("userId :: " + userId);
            responseEntity = new ResponseEntity<>(taskService.getAllTasksFromArchievedTaskList(userId), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    @DeleteMapping("user/archivedTaskList/{taskId}")
    public ResponseEntity<?> deleteTaskFromArchivedTaskList(@PathVariable UUID taskId,HttpServletRequest request) throws TaskNotFoundException, UserNotFoundException {
        // delete a task based on user id and task id, extract user id from claims
        // return 200 status if user is saved else 500 status
        Claims claims = (Claims) request.getAttribute("claims");
//        System.out.println("userId from claims :: " + claims.getSubject());
        String userId = claims.getSubject();
//        System.out.println("userId :: "+userId);
        try {
            responseEntity = new ResponseEntity<>(taskService.deleteTaskFromArchievedTaskList(userId, taskId), HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            throw new TaskNotFoundException();
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @PutMapping("user/updateTask")
    public ResponseEntity<?> updateTask(@RequestBody Task task, HttpServletRequest request) {
        try {
            // Extract the user ID from the request claims
            Claims claims = (Claims) request.getAttribute("claims");
            String userId = claims.getSubject();

            // Call the service to update the task
            System.out.println("inisde controller, updating task");
            List<Task> updatedTaskList = taskService.updateUserTaskListWithGivenTask(userId, task);

            return new ResponseEntity<>(updatedTaskList, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update the task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("user/markTaskAsCompleted/{taskId}")
    public ResponseEntity<?> markTaskAsCompleted(@PathVariable UUID taskId, HttpServletRequest request) {
        System.out.println("inside controller task completion: 1");
        try {
            // Extract the user ID from the request claims
            System.out.println("inside controller task completion: 2");
            Claims claims = (Claims) request.getAttribute("claims");
            String userId = claims.getSubject();

            // Call the service to mark the task as completed

            System.out.println("inside controller task completion: 3 ");
            Task completedTask = taskService.markTaskAsCompleted(userId, taskId);

            return new ResponseEntity<>(completedTask, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to mark the task as completed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "user/markComplete/{taskId}")
    public  ResponseEntity<?> markComplete(@PathVariable UUID taskId, HttpServletRequest request) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String userId = claims.getSubject();
            System.out.println("Inisde controller, marking task");
            Task updatedTask = taskService.markTaskAsCompleted(userId, taskId);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to mark the task", HttpStatus.CONFLICT);
        }
    }

}
