package com.taskmanager.domain.exception;

/**
 * Exception thrown when a task is not found.
 * Demonstrates custom exception hierarchy and proper error handling.
 */
public class TaskNotFoundException extends RuntimeException {
    private final String taskId;

    public TaskNotFoundException(String taskId) {
        super("Task not found with ID: " + taskId);
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }
}

