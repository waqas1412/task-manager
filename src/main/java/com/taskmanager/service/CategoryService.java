package com.taskmanager.service;

import com.taskmanager.domain.Category;
import com.taskmanager.domain.exception.CategoryNotFoundException;
import com.taskmanager.repository.CategoryRepository;
import com.taskmanager.repository.impl.JsonCategoryRepository;

import java.util.List;

/**
 * Service layer for category management operations.
 * Handles category-related business logic.
 */
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService() {
        this.categoryRepository = JsonCategoryRepository.getInstance();
    }

    /**
     * Constructor for dependency injection.
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Create a new category.
     * 
     * @param name category name
     * @param description category description
     * @param color category color (hex code)
     * @return created category
     */
    public Category createCategory(String name, String description, String color) {
        // Check if category with same name already exists
        categoryRepository.findByName(name).ifPresent(existing -> {
            throw new IllegalArgumentException("Category with name '" + name + "' already exists");
        });
        
        Category category = Category.create(name, description, color);
        return categoryRepository.save(category);
    }

    /**
     * Update category name.
     */
    public Category updateCategoryName(String categoryId, String newName) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        
        // Check if new name conflicts with existing category
        categoryRepository.findByName(newName).ifPresent(existing -> {
            if (!existing.id().equals(categoryId)) {
                throw new IllegalArgumentException("Category with name '" + newName + "' already exists");
            }
        });
        
        Category updated = category.withName(newName);
        return categoryRepository.save(updated);
    }

    /**
     * Update category description.
     */
    public Category updateCategoryDescription(String categoryId, String newDescription) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        
        Category updated = category.withDescription(newDescription);
        return categoryRepository.save(updated);
    }

    /**
     * Update category color.
     */
    public Category updateCategoryColor(String categoryId, String newColor) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        
        Category updated = category.withColor(newColor);
        return categoryRepository.save(updated);
    }

    /**
     * Get category by ID.
     */
    public Category getCategory(String categoryId) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    /**
     * Get category by name.
     */
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
            .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + name));
    }

    /**
     * Get all categories.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Delete a category.
     */
    public boolean deleteCategory(String categoryId) {
        return categoryRepository.deleteById(categoryId);
    }
}

