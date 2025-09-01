package com.taskmanager.repository;

import com.taskmanager.domain.Category;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Category persistence operations.
 * Demonstrates Repository pattern and Interface Segregation Principle.
 */
public interface CategoryRepository {
    /**
     * Save a category. Creates new or updates existing.
     * 
     * @param category the category to save
     * @return the saved category
     */
    Category save(Category category);

    /**
     * Find a category by its ID.
     * 
     * @param id the category ID
     * @return Optional containing the category if found
     */
    Optional<Category> findById(String id);

    /**
     * Find a category by name.
     * 
     * @param name the category name
     * @return Optional containing the category if found
     */
    Optional<Category> findByName(String name);

    /**
     * Find all categories.
     * 
     * @return list of all categories
     */
    List<Category> findAll();

    /**
     * Delete a category by ID.
     * 
     * @param id the category ID
     * @return true if deleted, false if not found
     */
    boolean deleteById(String id);

    /**
     * Delete all categories.
     */
    void deleteAll();

    /**
     * Count total number of categories.
     * 
     * @return category count
     */
    long count();
}

