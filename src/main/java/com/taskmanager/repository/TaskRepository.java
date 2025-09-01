package com.taskmanager.repository;

import com.taskmanager.domain.Task;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Task persistence operations.
 * Follows Repository pattern and Dependency Inversion Principle (SOLID).
 * This abstraction allows for different storage implementations (JSON, DB, etc.).
 */
public interface TaskRepository {
    /**
     * Save a task. Creates new or updates existing.
     * 
     * @param task the task to save
     * @return the saved task
     */
    Task save(Task task);

    /**
     * Find a task by its ID.
     * 
     * @param id the task ID
     * @return Optional containing the task if found
     */
    Optional<Task> findById(String id);

    /**
     * Find all tasks.
     * 
     * @return list of all tasks
     */
    List<Task> findAll();

    /**
     * Find tasks by category ID.
     * 
     * @param categoryId the category ID
     * @return list of tasks in the category
     */
    List<Task> findByCategoryId(String categoryId);

    /**
     * Delete a task by ID.
     * 
     * @param id the task ID
     * @return true if deleted, false if not found
     */
    boolean deleteById(String id);

    /**
     * Delete all tasks.
     */
    void deleteAll();

    /**
     * Count total number of tasks.
     * 
     * @return task count
     */
    long count();
}

