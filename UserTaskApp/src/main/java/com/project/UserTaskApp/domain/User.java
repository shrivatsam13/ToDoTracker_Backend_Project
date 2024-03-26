package com.project.UserTaskApp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class User {
    private String userId;
    private String userName;
    @Id
    private String userEmail;
    private String userPassword;
    private List<Task> userTaskList;
    private List<Task> archievedTaskList;

    public User() {
    }

    public User(String userId, String userName, String userEmail, String userPassword, List<Task> userTaskList, List<Task> archievedList) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userTaskList = userTaskList;
        this.archievedTaskList = archievedList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public List<Task> getUserTaskList() {
        return userTaskList;
    }

    public void setUserTaskList(List<Task> userTaskList) {
        this.userTaskList = userTaskList;
    }

    public List<Task> getArchievedTaskList() {
        return archievedTaskList;
    }

    public void setArchievedTaskList(List<Task> archievedTaskList) {
        this.archievedTaskList = archievedTaskList;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userTaskList=" + userTaskList +
                ", archievedTaskList=" + archievedTaskList +
                '}';
    }
}
