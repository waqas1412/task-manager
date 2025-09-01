package com.taskmanager.domain;

/**
 * Represents the priority level of a task.
 * Uses sealed interface to restrict implementations to predefined values.
 * This is a Java 25 best practice for type-safe enumerations.
 */
public enum Priority {
    LOW(1, "Low"),
    MEDIUM(2, "Medium"),
    HIGH(3, "High"),
    CRITICAL(4, "Critical");

    private final int level;
    private final String displayName;

    Priority(int level, String displayName) {
        this.level = level;
        this.displayName = displayName;
    }

    public int getLevel() {
        return level;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Parse priority from string input.
     * Demonstrates pattern matching with enhanced switch (Java 25).
     */
    public static Priority fromString(String value) {
        return switch (value.toUpperCase()) {
            case "LOW", "L", "1" -> LOW;
            case "MEDIUM", "M", "2" -> MEDIUM;
            case "HIGH", "H", "3" -> HIGH;
            case "CRITICAL", "C", "4" -> CRITICAL;
            default -> throw new IllegalArgumentException("Invalid priority: " + value);
        };
    }

    @Override
    public String toString() {
        return displayName;
    }
}

