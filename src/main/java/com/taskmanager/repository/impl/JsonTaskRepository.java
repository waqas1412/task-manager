package com.taskmanager.repository.impl;

import com.google.gson.reflect.TypeToken;
import com.taskmanager.domain.Task;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.util.JsonUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * JSON-based implementation of TaskRepository.
 * Demonstrates Repository pattern implementation with in-memory cache and file persistence.
 * Uses Singleton pattern for repository instance.
 */
public class JsonTaskRepository implements TaskRepository {
    private static final Path DATA_FILE = Paths.get("data", "tasks.json");
    private static JsonTaskRepository instance;
    
    private final Map<String, Task> taskCache;

    private JsonTaskRepository() {
        this.taskCache = new ConcurrentHashMap<>();
        loadFromFile();
    }

    /**
     * Get singleton instance.
     * Demonstrates thread-safe lazy initialization.
     */
    public static synchronized JsonTaskRepository getInstance() {
        if (instance == null) {
            instance = new JsonTaskRepository();
        }
        return instance;
    }

    /**
     * Load tasks from JSON file into cache.
     */
    private void loadFromFile() {
        try {
            String json = java.nio.file.Files.readString(DATA_FILE);
            List<Task> tasks = new com.google.gson.Gson().fromJson(
                json, 
                new TypeToken<List<Task>>(){}.getType()
            );
            
            if (tasks != null) {
                tasks.forEach(task -> taskCache.put(task.getId(), task));
            }
        } catch (Exception e) {
            // File doesn't exist or is empty, start with empty cache
            taskCache.clear();
        }
    }

    /**
     * Save all tasks to JSON file.
     * Uses virtual threads for async I/O (Java 25 feature).
     */
    private void saveToFile() {
        List<Task> tasks = new ArrayList<>(taskCache.values());
        JsonUtil.writeToFile(tasks, DATA_FILE);
    }

    @Override
    public Task save(Task task) {
        Objects.requireNonNull(task, "Task cannot be null");
        taskCache.put(task.getId(), task);
        saveToFile();
        return task;
    }

    @Override
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(taskCache.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(taskCache.values());
    }

    @Override
    public List<Task> findByCategoryId(String categoryId) {
        return taskCache.values().stream()
            .filter(task -> Objects.equals(task.getCategoryId(), categoryId))
            .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(String id) {
        Task removed = taskCache.remove(id);
        if (removed != null) {
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public void deleteAll() {
        taskCache.clear();
        saveToFile();
    }

    @Override
    public long count() {
        return taskCache.size();
    }
}

