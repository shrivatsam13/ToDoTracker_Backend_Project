package com.project.UserTaskApp.service;

import com.project.UserTaskApp.domain.Task;
import com.project.UserTaskApp.domain.User;
import com.project.UserTaskApp.exceptions.TaskAlreadyExistsException;
import com.project.UserTaskApp.exceptions.TaskNotFoundException;
import com.project.UserTaskApp.exceptions.UserAlreadyExistsException;
import com.project.UserTaskApp.exceptions.UserNotFoundException;
import com.project.UserTaskApp.proxy.UserProxy;
import com.project.UserTaskApp.repository.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService{
    private UserTaskRepository userTaskRepository;

    private UserProxy userProxy;

    @Autowired
    public TaskServiceImpl(UserTaskRepository userTaskRepository, UserProxy userProxy) {
        this.userTaskRepository = userTaskRepository;
        this.userProxy = userProxy;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        // Register a new user
        if(userTaskRepository.findUserByUserEmail(user.getUserEmail()) != null)
        {
            throw new UserAlreadyExistsException();
        }
        //return userTaskRepository.save(user);
        User savedUser = userTaskRepository.save(user);

        if(!(savedUser.getUserEmail().isEmpty())) {
            ResponseEntity response = userProxy.saveUser(user);
            System.out.println(response);
        }
        return savedUser;
    }

    @Override
    public String removeUser(User user) throws UserNotFoundException {
        if(userTaskRepository.findUserByUserEmail(user.getUserEmail()) == null )
        {
            throw new UserNotFoundException();
        }
        userTaskRepository.delete(user);
        if(!(user.getUserEmail().isEmpty())) {
            String response = userProxy.deleteUser(user);
            System.out.println(response);
        }
        return "User removed successfully from mongo";
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList =  userTaskRepository.findAll();
        return userList;
    }

    @Override
    public List<Task> saveTaskToTaskList(Task task, String userEmail) throws TaskAlreadyExistsException, UserNotFoundException {
        // Retrieve the user by ID
        User user = userTaskRepository.findUserByUserEmail(userEmail);

        if (user == null) {
            throw new UserNotFoundException();
        }

        // Check if the task already exists in the user's taskList
        if (user.getUserTaskList() != null && user.getUserTaskList().stream().anyMatch(t -> t.getTaskId() == task.getTaskId())) {
            throw new TaskAlreadyExistsException();
        }

        // Add the task to the user's taskList
        if (user.getUserTaskList() == null) {
            user.setUserTaskList(Collections.singletonList(task));
        } else {
            List<Task> tasks = new ArrayList<>(user.getUserTaskList());
            tasks.add(task);
            user.setUserTaskList(tasks);
        }
        userTaskRepository.save(user);
//        return userTaskRepository.save(user);
        return user.getUserTaskList();
    }

    @Override
    public List<Task> getAllTasksFromTaskList(String userEmail) throws Exception {
        User user = userTaskRepository.findUserByUserEmail(userEmail);

        if (user == null ) {
            throw new UserNotFoundException();
        }

        // Return the list of tasks in the user's task list
        System.out.println(user.getUserTaskList());
        // Check if the task due date has passed
        archiveTasksIfDueDatePassed(user);
        return user.getUserTaskList();
    }

    @Override
    public void archiveTasksIfDueDatePassed(User user) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate currentDate = LocalDate.now();

        System.out.println("Entered for checking dueDate wrt: " + currentDate);

        List<Task> userTaskList = user.getUserTaskList();
        System.out.println("Tasks in DB: " + userTaskList);

        if (userTaskList != null) {
            List<Task> tasksToArchive = new ArrayList<>();

            for (Task task : userTaskList) {
                String dueDateString = task.getTaskDueDate();
                LocalDate dueDate = LocalDate.parse(dueDateString, dateFormatter);
                System.out.println("Printing dueDate of Task: " + dueDate);
                System.out.println("Printing dueDate before or not : " + dueDate.isBefore(currentDate));

                Boolean isAlreadyPresent = user.getArchievedTaskList().contains(task);

                if (dueDate != null && !task.isComplete() && !isAlreadyPresent && dueDate.isBefore(currentDate)) {
                    System.out.println("Add task to archive");
                    tasksToArchive.add(task);
                }
            }

            if (!tasksToArchive.isEmpty()) {
                System.out.println("We have tasks that are passed and need to be added :" + tasksToArchive);
                // Move tasks to archived task list
                List<Task> archivedTaskList = user.getArchievedTaskList();
                if (archivedTaskList == null) {
                    archivedTaskList = new ArrayList<>();
                }
                archivedTaskList.addAll(tasksToArchive);
                System.out.println("Added task to archive");
                user.setArchievedTaskList(archivedTaskList);
                System.out.println("Updated task to archive");

                // Remove tasks from the current task list
                userTaskList.removeAll(tasksToArchive);
                System.out.println("Removed task from task list");

                // Save the updated user
                userTaskRepository.save(user);
                System.out.println("Updated list for user tasks: " + user.getUserTaskList());
                System.out.println("Updated archived list for user tasks: " + user.getArchievedTaskList());
            }
        }
    }

    @Override
    public User deleteTaskFromTaskList(String userId, UUID taskId) throws TaskNotFoundException, UserNotFoundException {
        User user = userTaskRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        List<Task> tasks = user.getUserTaskList();
        boolean taskIdIsPresent = false;

        // Check if the provided taskId exists in the user's task list
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                taskIdIsPresent = true;
                tasks.remove(task);
                break;
            }
        }

        if (!taskIdIsPresent) {
            throw new TaskNotFoundException();
        }

        user.setUserTaskList(tasks);
        return userTaskRepository.save(user);
    }

    @Override
    public User saveTaskToArchievedTaskList(Task task, String userEmail) throws TaskAlreadyExistsException, UserNotFoundException, TaskNotFoundException {
        // Retrieve the user by ID
        User user = userTaskRepository.findUserByUserEmail(userEmail);

        if (user == null) {
            throw new UserNotFoundException();
        }

        // Check if the task already exists in the user's taskList

        if (user.getArchievedTaskList() != null && user.getArchievedTaskList().stream().anyMatch(t -> t.getTaskId().equals(task.getTaskId()))) {
            throw new TaskAlreadyExistsException();
        }

        // Add the task to the user's archivedTaskList
        if (user.getArchievedTaskList() == null) {
            user.setArchievedTaskList(Collections.singletonList(task));
        } else {
            List<Task> tasks = new ArrayList<>(user.getArchievedTaskList());
            tasks.add(task);
            user.setArchievedTaskList(tasks);
        }
        //save new task into archiveList
        userTaskRepository.save(user);

        //code to remove the task from taskList after adding to archivedTaskList
        UUID taskId = task.getTaskId();
        User updateUser = deleteTaskFromTaskList(userEmail, taskId);

        return updateUser;
    }

    @Override
    public List<Task> getAllTasksFromArchievedTaskList(String userEmail) throws Exception {
        User user = userTaskRepository.findUserByUserEmail(userEmail);

        if (user == null ) {
            throw new UserNotFoundException();
        }

        // Return the list of tasks in the user's task list
        return user.getArchievedTaskList();
    }

    @Override
    public User deleteTaskFromArchievedTaskList(String userEmail, UUID taskId) throws TaskNotFoundException, UserNotFoundException{
        User user = userTaskRepository.findUserByUserEmail(userEmail);

        if (user == null) {
            throw new UserNotFoundException();
        }

        List<Task> tasks = user.getArchievedTaskList();
        boolean taskIdIsPresent = false;

        // Check if the provided taskId exists in the user's task list
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)){
                taskIdIsPresent = true;
                tasks.remove(task);
                break;
            }
        }

        if (!taskIdIsPresent) {
            throw new TaskNotFoundException();
        }

        user.setArchievedTaskList(tasks);
        return userTaskRepository.save(user);
    }

    @Override
    public User moveTaskFromArchiveToTaskList(String userEmail, Task task) throws TaskNotFoundException, UserNotFoundException, TaskAlreadyExistsException {
        // Retrieve the user by ID
        User user = userTaskRepository.findUserByUserEmail(userEmail);

        if (user == null) {
            throw new UserNotFoundException();
        }

        // Check if the task already exists in the user's taskList
        if (user.getUserTaskList() != null && user.getUserTaskList().stream().anyMatch(t -> t.getTaskId() == task.getTaskId())) {
            throw new TaskAlreadyExistsException();
        }

        // Add the task to the userTaskList
        if (user.getUserTaskList() == null) {
            user.setUserTaskList(Collections.singletonList(task));
        } else {
            List<Task> tasks = new ArrayList<>(user.getUserTaskList());
            tasks.add(task);
            user.setUserTaskList(tasks);
        }
        //save new task into archiveList
        userTaskRepository.save(user);

        //code to remove the task from taskList after adding to archivedTaskList
        UUID taskId = task.getTaskId();
        User updateUser = deleteTaskFromArchievedTaskList(userEmail, taskId);

        return updateUser;
    }

    @Override
    public List<Task> updateUserTaskListWithGivenTask(String userId, Task task) throws UserNotFoundException, TaskNotFoundException, TaskAlreadyExistsException {
        // Update the specific details of User
        // Retrieve the user by ID
        User user = userTaskRepository.findById(userId).orElse(null);
        System.out.println(task);
        if (user == null) {
            throw new UserNotFoundException();
        }

        List<Task> tasks = user.getUserTaskList();
        boolean taskIdIsPresent = false;

        // Check if the provided task exists in the user's task list
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("Entered 1");

            Task existingTask = tasks.get(i);

            // If the task with the same ID is found, update it
            if (existingTask.getTaskId().equals(task.getTaskId())) {
                // Check if the task with the same name already exists in the user's wish list
                if (tasks.stream().anyMatch(t -> t != existingTask && t.getTaskTitle().equals(task.getTaskTitle())))
                {
                    throw new TaskAlreadyExistsException();
                }
                System.out.println("Entered 2");
                // Update the task details
                existingTask.setTaskTitle(task.getTaskTitle());
                existingTask.setTaskContent(task.getTaskContent());
                existingTask.setTaskPriority(task.getTaskPriority());
                existingTask.setTaskDueDate(task.getTaskDueDate());
                existingTask.setComplete(task.isComplete());
                System.out.println("Entered 3");
                // Save the updated user
                userTaskRepository.save(user);
                System.out.println("Entered 4, Updated");
                System.out.println(userTaskRepository.save(user));
                return user.getUserTaskList();
            }
        }

        // If the task doesn't exist in the user's wish list, throw a TaskNotFoundException
        throw new TaskNotFoundException();
    }

    @Override
    public List<Task> updateArchivedTaskListWithGivenTask(String userId, Task task) throws UserNotFoundException, TaskNotFoundException, TaskAlreadyExistsException {
        // Update the specific details of User
        // Retrieve the user by ID
        User user = userTaskRepository.findById(userId).orElse(null);
        System.out.println(task);
        if (user == null) {
            throw new UserNotFoundException();
        }

        List<Task> tasks = user.getArchievedTaskList();
        boolean taskIdIsPresent = false;

        // Check if the provided task exists in the user's task list
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("Entered 1");

            Task existingTask = tasks.get(i);

            // If the task with the same ID is found, update it
            if (existingTask.getTaskId().equals(task.getTaskId())) {
                // Check if the task with the same name already exists in the user's wish list
                if (tasks.stream().anyMatch(t -> t != existingTask && t.getTaskTitle().equals(task.getTaskTitle())))
                {
                    throw new TaskAlreadyExistsException();
                }
                System.out.println("Entered 2");
                // Update the task details
                existingTask.setTaskTitle(task.getTaskTitle());
                existingTask.setTaskContent(task.getTaskContent());
                existingTask.setTaskPriority(task.getTaskPriority());
                existingTask.setTaskDueDate(task.getTaskDueDate());
                existingTask.setComplete(task.isComplete());
                System.out.println("Entered 3");
                // Save the updated user
                userTaskRepository.save(user);
                System.out.println("Entered 4, Updated");
                System.out.println(userTaskRepository.save(user));
                return user.getUserTaskList();
            }
        }

        // If the task doesn't exist in the user's wish list, throw a TaskNotFoundException
        throw new TaskNotFoundException();
    }

    public Task markTaskAsCompleted(String userId, UUID taskId) throws UserNotFoundException, TaskNotFoundException {
        System.out.println("Inside Service");
        User user = userTaskRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Task task = user.getUserTaskList().stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst()
                .orElseThrow(TaskNotFoundException::new);

        // Update the task completion status
        task.setComplete(true);
        System.out.println("inside service task completion: "+task.isComplete());

        // Save the updated user with the completed task
        userTaskRepository.save(user);

        return task;
    }

}
