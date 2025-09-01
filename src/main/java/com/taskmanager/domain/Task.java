package com.taskmanager.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a task in the task management system.
 * Demonstrates proper domain modeling with encapsulation and validation.
 * Uses immutable design with builder pattern for flexibility.
 */
public final class Task {
    private final String id;
    private final String title;
    private final String description;
    private final Priority priority;
    private final Status status;
    private final String categoryId;
    private final LocalDateTime dueDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Task(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.priority = builder.priority;
        this.status = builder.status;
        this.categoryId = builder.categoryId;
        this.dueDate = builder.dueDate;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Priority getPriority() { return priority; }
    public Status getStatus() { return status; }
    public String getCategoryId() { return categoryId; }
    public LocalDateTime getDueDate() { return dueDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    /**
     * Check if task is overdue.
     */
    public boolean isOverdue() {
        return dueDate != null && 
               status != Status.DONE && 
               LocalDateTime.now().isAfter(dueDate);
    }

    /**
     * Check if task is due soon (within 24 hours).
     */
    public boolean isDueSoon() {
        return dueDate != null && 
               status != Status.DONE && 
               LocalDateTime.now().plusHours(24).isAfter(dueDate) &&
               !isOverdue();
    }

    /**
     * Create a copy with updated status.
     * Validates state transition before creating new instance.
     */
    public Task withStatus(Status newStatus) {
        if (!this.status.canTransitionTo(newStatus)) {
            throw new IllegalStateException(
                String.format("Cannot transition from %s to %s", this.status, newStatus)
            );
        }
        return new Builder(this)
            .status(newStatus)
            .updatedAt(LocalDateTime.now())
            .build();
    }

    /**
     * Create a copy with updated priority.
     */
    public Task withPriority(Priority newPriority) {
        return new Builder(this)
            .priority(newPriority)
            .updatedAt(LocalDateTime.now())
            .build();
    }

    /**
     * Create a copy with updated title.
     */
    public Task withTitle(String newTitle) {
        return new Builder(this)
            .title(newTitle)
            .updatedAt(LocalDateTime.now())
            .build();
    }

    /**
     * Create a copy with updated description.
     */
    public Task withDescription(String newDescription) {
        return new Builder(this)
            .description(newDescription)
            .updatedAt(LocalDateTime.now())
            .build();
    }

    /**
     * Create a copy with updated due date.
     */
    public Task withDueDate(LocalDateTime newDueDate) {
        return new Builder(this)
            .dueDate(newDueDate)
            .updatedAt(LocalDateTime.now())
            .build();
    }

    /**
     * Create a copy with updated category.
     */
    public Task withCategory(String newCategoryId) {
        return new Builder(this)
            .categoryId(newCategoryId)
            .updatedAt(LocalDateTime.now())
            .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", dueDate=" + dueDate +
                '}';
    }

    /**
     * Builder pattern for flexible Task creation.
     * Demonstrates GoF Builder pattern implementation.
     */
    public static class Builder {
        private String id;
        private String title;
        private String description;
        private Priority priority;
        private Status status;
        private String categoryId;
        private LocalDateTime dueDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder() {
            this.id = UUID.randomUUID().toString();
            this.priority = Priority.MEDIUM;
            this.status = Status.TODO;
            this.description = "";
            this.createdAt = LocalDateTime.now();
            this.updatedAt = LocalDateTime.now();
        }

        /**
         * Copy constructor for creating modified copies.
         */
        public Builder(Task task) {
            this.id = task.id;
            this.title = task.title;
            this.description = task.description;
            this.priority = task.priority;
            this.status = task.status;
            this.categoryId = task.categoryId;
            this.dueDate = task.dueDate;
            this.createdAt = task.createdAt;
            this.updatedAt = task.updatedAt;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder categoryId(String categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder dueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Task build() {
            Objects.requireNonNull(title, "Task title cannot be null");
            if (title.isBlank()) {
                throw new IllegalArgumentException("Task title cannot be blank");
            }
            return new Task(this);
        }
    }
}

