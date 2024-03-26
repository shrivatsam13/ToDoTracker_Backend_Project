package com.project.UserTaskApp.domain;

import org.springframework.data.annotation.Id;
import java.util.UUID;

public class Task {
    @Id
    private UUID taskId = UUID.randomUUID();
    private String taskTitle;
    private String taskContent;
    private String taskCreatedDate;
    private String taskDueDate;
    private String taskPriority;
    private boolean isComplete = false;

    public Task() {
    }

    public Task(UUID taskId, String taskTitle, String taskContent, String taskCreatedDate, String taskDueDate, String taskPriority, boolean isComplete) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskContent = taskContent;
        this.taskCreatedDate = taskCreatedDate;
        this.taskDueDate = taskDueDate;
        this.taskPriority = taskPriority;
        this.isComplete = isComplete;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskCreatedDate() {
        return taskCreatedDate;
    }

    public void setTaskCreatedDate(String taskCreatedDate) {
        this.taskCreatedDate = taskCreatedDate;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(String taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskContent='" + taskContent + '\'' +
                ", taskCreatedDate='" + taskCreatedDate + '\'' +
                ", taskDueDate='" + taskDueDate + '\'' +
                ", taskPriority='" + taskPriority + '\'' +
                ", isComplete=" + isComplete +
                '}';
    }
}
