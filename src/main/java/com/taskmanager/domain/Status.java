package com.taskmanager.domain;

/**
 * Represents the current status of a task.
 * Follows the state pattern for task lifecycle management.
 */
public enum Status {
    TODO("To Do", "Task is pending"),
    IN_PROGRESS("In Progress", "Task is being worked on"),
    DONE("Done", "Task is completed"),
    CANCELLED("Cancelled", "Task was cancelled");

    private final String displayName;
    private final String description;

    Status(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Parse status from string input.
     * Uses enhanced switch expressions (Java 25).
     */
    public static Status fromString(String value) {
        return switch (value.toUpperCase().replace(" ", "_")) {
            case "TODO", "TO_DO", "PENDING", "1" -> TODO;
            case "IN_PROGRESS", "INPROGRESS", "PROGRESS", "2" -> IN_PROGRESS;
            case "DONE", "COMPLETED", "COMPLETE", "3" -> DONE;
            case "CANCELLED", "CANCELED", "4" -> CANCELLED;
            default -> throw new IllegalArgumentException("Invalid status: " + value);
        };
    }

    /**
     * Check if task can transition to another status.
     * Demonstrates business logic in domain model.
     */
    public boolean canTransitionTo(Status newStatus) {
        return switch (this) {
            case TODO -> newStatus == IN_PROGRESS || newStatus == CANCELLED;
            case IN_PROGRESS -> newStatus == DONE || newStatus == TODO || newStatus == CANCELLED;
            case DONE -> newStatus == TODO; // Allow reopening
            case CANCELLED -> newStatus == TODO; // Allow reactivation
        };
    }

    @Override
    public String toString() {
        return displayName;
    }
}

