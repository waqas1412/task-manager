package com.taskmanager.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Status enum.
 */
class StatusTest {

    @Test
    @DisplayName("Should parse status from string")
    void shouldParseStatusFromString() {
        assertEquals(Status.TODO, Status.fromString("TODO"));
        assertEquals(Status.IN_PROGRESS, Status.fromString("IN_PROGRESS"));
        assertEquals(Status.DONE, Status.fromString("DONE"));
        assertEquals(Status.CANCELLED, Status.fromString("CANCELLED"));
    }

    @Test
    @DisplayName("Should handle alternative status names")
    void shouldHandleAlternativeStatusNames() {
        assertEquals(Status.TODO, Status.fromString("PENDING"));
        assertEquals(Status.IN_PROGRESS, Status.fromString("PROGRESS"));
        assertEquals(Status.DONE, Status.fromString("COMPLETED"));
    }

    @Test
    @DisplayName("Should validate TODO transitions")
    void shouldValidateTodoTransitions() {
        Status todo = Status.TODO;
        
        assertTrue(todo.canTransitionTo(Status.IN_PROGRESS));
        assertTrue(todo.canTransitionTo(Status.CANCELLED));
        assertFalse(todo.canTransitionTo(Status.DONE));
        assertFalse(todo.canTransitionTo(Status.TODO));
    }

    @Test
    @DisplayName("Should validate IN_PROGRESS transitions")
    void shouldValidateInProgressTransitions() {
        Status inProgress = Status.IN_PROGRESS;
        
        assertTrue(inProgress.canTransitionTo(Status.DONE));
        assertTrue(inProgress.canTransitionTo(Status.TODO));
        assertTrue(inProgress.canTransitionTo(Status.CANCELLED));
        assertFalse(inProgress.canTransitionTo(Status.IN_PROGRESS));
    }

    @Test
    @DisplayName("Should validate DONE transitions")
    void shouldValidateDoneTransitions() {
        Status done = Status.DONE;
        
        assertTrue(done.canTransitionTo(Status.TODO));
        assertFalse(done.canTransitionTo(Status.IN_PROGRESS));
        assertFalse(done.canTransitionTo(Status.CANCELLED));
        assertFalse(done.canTransitionTo(Status.DONE));
    }

    @Test
    @DisplayName("Should validate CANCELLED transitions")
    void shouldValidateCancelledTransitions() {
        Status cancelled = Status.CANCELLED;
        
        assertTrue(cancelled.canTransitionTo(Status.TODO));
        assertFalse(cancelled.canTransitionTo(Status.IN_PROGRESS));
        assertFalse(cancelled.canTransitionTo(Status.DONE));
        assertFalse(cancelled.canTransitionTo(Status.CANCELLED));
    }

    @Test
    @DisplayName("Should throw exception for invalid status")
    void shouldThrowExceptionForInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Status.fromString("INVALID");
        });
    }
}

