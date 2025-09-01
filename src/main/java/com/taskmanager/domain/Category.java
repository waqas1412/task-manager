package com.taskmanager.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a task category.
 * Uses Java record for immutable data class (Java 14+, best practice in Java 25).
 * Records automatically generate constructor, getters, equals, hashCode, and toString.
 */
public record Category(
    String id,
    String name,
    String description,
    String color
) {
    /**
     * Compact constructor for validation.
     * Demonstrates Java record validation pattern.
     */
    public Category {
        Objects.requireNonNull(id, "Category ID cannot be null");
        Objects.requireNonNull(name, "Category name cannot be null");
        
        if (name.isBlank()) {
            throw new IllegalArgumentException("Category name cannot be blank");
        }
        
        if (description == null) {
            description = "";
        }
        
        if (color == null) {
            color = "#808080"; // Default gray
        }
    }

    /**
     * Factory method to create a new category with generated ID.
     * Demonstrates factory pattern with records.
     */
    public static Category create(String name, String description, String color) {
        return new Category(
            UUID.randomUUID().toString(),
            name,
            description,
            color
        );
    }

    /**
     * Create a copy with updated name.
     * Records are immutable, so we create new instances for updates.
     */
    public Category withName(String newName) {
        return new Category(this.id, newName, this.description, this.color);
    }

    /**
     * Create a copy with updated description.
     */
    public Category withDescription(String newDescription) {
        return new Category(this.id, this.name, newDescription, this.color);
    }

    /**
     * Create a copy with updated color.
     */
    public Category withColor(String newColor) {
        return new Category(this.id, this.name, this.description, newColor);
    }
}

