package com.taskmanager.domain.exception;

/**
 * Exception thrown when a category is not found.
 */
public class CategoryNotFoundException extends RuntimeException {
    private final String categoryId;

    public CategoryNotFoundException(String categoryId) {
        super("Category not found with ID: " + categoryId);
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }
}

