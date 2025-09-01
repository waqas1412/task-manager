package com.taskmanager.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

/**
 * Unit tests for Task domain model.
 * Demonstrates JUnit 5 testing best practices.
 */
class TaskTest {

    @Test
    @DisplayName("Should create task with builder")
    void shouldCreateTaskWithBuilder() {
        Task task = new Task.Builder()
            .title("Test Task")
            .description("Test Description")
            .priority(Priority.HIGH)
            .build();

        assertNotNull(task);
        assertEquals("Test Task", task.getTitle());
        assertEquals("Test Description", task.getDescription());
        assertEquals(Priority.HIGH, task.getPriority());
        assertEquals(Status.TODO, task.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when title is null")
    void shouldThrowExceptionWhenTitleIsNull() {
        assertThrows(NullPointerException.class, () -> {
            new Task.Builder().build();
        });
    }

    @Test
    @DisplayName("Should throw exception when title is blank")
    void shouldThrowExceptionWhenTitleIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Task.Builder().title("   ").build();
        });
    }

    @Test
    @DisplayName("Should detect overdue task")
    void shouldDetectOverdueTask() {
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);
        Task task = new Task.Builder()
            .title("Overdue Task")
            .dueDate(pastDate)
            .build();

        assertTrue(task.isOverdue());
    }

    @Test
    @DisplayName("Should detect task due soon")
    void shouldDetectTaskDueSoon() {
        LocalDateTime soonDate = LocalDateTime.now().plusHours(12);
        Task task = new Task.Builder()
            .title("Due Soon Task")
            .dueDate(soonDate)
            .build();

        assertTrue(task.isDueSoon());
        assertFalse(task.isOverdue());
    }

    @Test
    @DisplayName("Should update task status")
    void shouldUpdateTaskStatus() {
        Task task = new Task.Builder()
            .title("Test Task")
            .build();

        Task updated = task.withStatus(Status.IN_PROGRESS);

        assertEquals(Status.TODO, task.getStatus());
        assertEquals(Status.IN_PROGRESS, updated.getStatus());
        assertNotSame(task, updated);
    }

    @Test
    @DisplayName("Should validate status transition")
    void shouldValidateStatusTransition() {
        Task task = new Task.Builder()
            .title("Test Task")
            .status(Status.DONE)
            .build();

        assertThrows(IllegalStateException.class, () -> {
            task.withStatus(Status.IN_PROGRESS);
        });
    }

    @Test
    @DisplayName("Should update task priority")
    void shouldUpdateTaskPriority() {
        Task task = new Task.Builder()
            .title("Test Task")
            .priority(Priority.LOW)
            .build();

        Task updated = task.withPriority(Priority.CRITICAL);

        assertEquals(Priority.LOW, task.getPriority());
        assertEquals(Priority.CRITICAL, updated.getPriority());
    }

    @Test
    @DisplayName("Should maintain immutability")
    void shouldMaintainImmutability() {
        Task original = new Task.Builder()
            .title("Original Task")
            .description("Original Description")
            .priority(Priority.MEDIUM)
            .build();

        Task modified = original.withTitle("Modified Task");

        assertEquals("Original Task", original.getTitle());
        assertEquals("Modified Task", modified.getTitle());
        assertEquals(original.getId(), modified.getId());
    }
}

