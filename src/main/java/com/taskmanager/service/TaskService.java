package com.taskmanager.service;

import com.taskmanager.domain.Priority;
import com.taskmanager.domain.Status;
import com.taskmanager.domain.Task;
import com.taskmanager.domain.exception.TaskNotFoundException;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.impl.JsonTaskRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer for task management operations.
 * Demonstrates Service Layer pattern and business logic encapsulation.
 * Follows Single Responsibility Principle - handles task-related business logic.
 */
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService() {
        this.taskRepository = JsonTaskRepository.getInstance();
    }

    /**
     * Constructor for dependency injection (useful for testing).
     * Demonstrates Dependency Inversion Principle.
     */
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Create a new task.
     * 
     * @param title task title
     * @param description task description
     * @param priority task priority
     * @param categoryId category ID (optional)
     * @param dueDate due date (optional)
     * @return created task
     */
    public Task createTask(String title, String description, Priority priority, 
                          String categoryId, LocalDateTime dueDate) {
        Task task = new Task.Builder()
            .title(title)
            .description(description != null ? description : "")
            .priority(priority != null ? priority : Priority.MEDIUM)
            .categoryId(categoryId)
            .dueDate(dueDate)
            .build();
        
        return taskRepository.save(task);
    }

    /**
     * Update task status.
     * Validates state transition before updating.
     * 
     * @param taskId task ID
     * @param newStatus new status
     * @return updated task
     * @throws TaskNotFoundException if task not found
     */
    public Task updateTaskStatus(String taskId, Status newStatus) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));
        
        Task updatedTask = task.withStatus(newStatus);
        return taskRepository.save(updatedTask);
    }

    /**
     * Update task priority.
     * 
     * @param taskId task ID
     * @param newPriority new priority
     * @return updated task
     */
    public Task updateTaskPriority(String taskId, Priority newPriority) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));
        
        Task updatedTask = task.withPriority(newPriority);
        return taskRepository.save(updatedTask);
    }

    /**
     * Update task title.
     */
    public Task updateTaskTitle(String taskId, String newTitle) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));
        
        Task updatedTask = task.withTitle(newTitle);
        return taskRepository.save(updatedTask);
    }

    /**
     * Update task description.
     */
    public Task updateTaskDescription(String taskId, String newDescription) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));
        
        Task updatedTask = task.withDescription(newDescription);
        return taskRepository.save(updatedTask);
    }

    /**
     * Update task due date.
     */
    public Task updateTaskDueDate(String taskId, LocalDateTime newDueDate) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));
        
        Task updatedTask = task.withDueDate(newDueDate);
        return taskRepository.save(updatedTask);
    }

    /**
     * Update task category.
     */
    public Task updateTaskCategory(String taskId, String categoryId) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));
        
        Task updatedTask = task.withCategory(categoryId);
        return taskRepository.save(updatedTask);
    }

    /**
     * Get task by ID.
     */
    public Task getTask(String taskId) {
        return taskRepository.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    /**
     * Get all tasks.
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Get tasks by category.
     */
    public List<Task> getTasksByCategory(String categoryId) {
        return taskRepository.findByCategoryId(categoryId);
    }

    /**
     * Delete a task.
     */
    public boolean deleteTask(String taskId) {
        return taskRepository.deleteById(taskId);
    }

    /**
     * Get task statistics.
     */
    public TaskStatistics getStatistics() {
        List<Task> allTasks = taskRepository.findAll();
        
        long total = allTasks.size();
        long todo = allTasks.stream().filter(t -> t.getStatus() == Status.TODO).count();
        long inProgress = allTasks.stream().filter(t -> t.getStatus() == Status.IN_PROGRESS).count();
        long done = allTasks.stream().filter(t -> t.getStatus() == Status.DONE).count();
        long overdue = allTasks.stream().filter(Task::isOverdue).count();
        
        return new TaskStatistics(total, todo, inProgress, done, overdue);
    }

    /**
     * Record for task statistics.
     * Demonstrates Java record usage for DTOs.
     */
    public record TaskStatistics(
        long total,
        long todo,
        long inProgress,
        long done,
        long overdue
    ) {}
}

