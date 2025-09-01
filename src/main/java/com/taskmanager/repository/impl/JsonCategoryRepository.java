package com.taskmanager.repository.impl;

import com.google.gson.reflect.TypeToken;
import com.taskmanager.domain.Category;
import com.taskmanager.repository.CategoryRepository;
import com.taskmanager.util.JsonUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JSON-based implementation of CategoryRepository.
 * Mirrors TaskRepository pattern for consistency.
 */
public class JsonCategoryRepository implements CategoryRepository {
    private static final Path DATA_FILE = Paths.get("data", "categories.json");
    private static JsonCategoryRepository instance;
    
    private final Map<String, Category> categoryCache;

    private JsonCategoryRepository() {
        this.categoryCache = new ConcurrentHashMap<>();
        loadFromFile();
        initializeDefaultCategories();
    }

    /**
     * Get singleton instance.
     */
    public static synchronized JsonCategoryRepository getInstance() {
        if (instance == null) {
            instance = new JsonCategoryRepository();
        }
        return instance;
    }

    /**
     * Initialize default categories if none exist.
     */
    private void initializeDefaultCategories() {
        if (categoryCache.isEmpty()) {
            List<Category> defaults = List.of(
                Category.create("Work", "Work-related tasks", "#3498db"),
                Category.create("Personal", "Personal tasks", "#2ecc71"),
                Category.create("Shopping", "Shopping list items", "#e74c3c"),
                Category.create("Health", "Health and fitness", "#9b59b6"),
                Category.create("Learning", "Study and learning", "#f39c12")
            );
            
            defaults.forEach(category -> categoryCache.put(category.id(), category));
            saveToFile();
        }
    }

    /**
     * Load categories from JSON file.
     */
    private void loadFromFile() {
        try {
            String json = java.nio.file.Files.readString(DATA_FILE);
            List<Category> categories = new com.google.gson.Gson().fromJson(
                json,
                new TypeToken<List<Category>>(){}.getType()
            );
            
            if (categories != null) {
                categories.forEach(cat -> categoryCache.put(cat.id(), cat));
            }
        } catch (Exception e) {
            // File doesn't exist, start with empty cache
            categoryCache.clear();
        }
    }

    /**
     * Save all categories to JSON file.
     */
    private void saveToFile() {
        List<Category> categories = new ArrayList<>(categoryCache.values());
        JsonUtil.writeToFile(categories, DATA_FILE);
    }

    @Override
    public Category save(Category category) {
        Objects.requireNonNull(category, "Category cannot be null");
        categoryCache.put(category.id(), category);
        saveToFile();
        return category;
    }

    @Override
    public Optional<Category> findById(String id) {
        return Optional.ofNullable(categoryCache.get(id));
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryCache.values().stream()
            .filter(cat -> cat.name().equalsIgnoreCase(name))
            .findFirst();
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categoryCache.values());
    }

    @Override
    public boolean deleteById(String id) {
        Category removed = categoryCache.remove(id);
        if (removed != null) {
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public void deleteAll() {
        categoryCache.clear();
        saveToFile();
    }

    @Override
    public long count() {
        return categoryCache.size();
    }
}

