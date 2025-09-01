package com.taskmanager.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Priority enum.
 */
class PriorityTest {

    @Test
    @DisplayName("Should have correct priority levels")
    void shouldHaveCorrectPriorityLevels() {
        assertEquals(1, Priority.LOW.getLevel());
        assertEquals(2, Priority.MEDIUM.getLevel());
        assertEquals(3, Priority.HIGH.getLevel());
        assertEquals(4, Priority.CRITICAL.getLevel());
    }

    @ParameterizedTest
    @CsvSource({
        "LOW, LOW",
        "MEDIUM, MEDIUM",
        "HIGH, HIGH",
        "CRITICAL, CRITICAL",
        "L, LOW",
        "M, MEDIUM",
        "H, HIGH",
        "C, CRITICAL",
        "1, LOW",
        "2, MEDIUM",
        "3, HIGH",
        "4, CRITICAL"
    })
    @DisplayName("Should parse priority from various string formats")
    void shouldParsePriorityFromString(String input, Priority expected) {
        assertEquals(expected, Priority.fromString(input));
    }

    @Test
    @DisplayName("Should throw exception for invalid priority")
    void shouldThrowExceptionForInvalidPriority() {
        assertThrows(IllegalArgumentException.class, () -> {
            Priority.fromString("INVALID");
        });
    }

    @Test
    @DisplayName("Should handle case-insensitive input")
    void shouldHandleCaseInsensitiveInput() {
        assertEquals(Priority.HIGH, Priority.fromString("high"));
        assertEquals(Priority.HIGH, Priority.fromString("HIGH"));
        assertEquals(Priority.HIGH, Priority.fromString("High"));
    }
}

