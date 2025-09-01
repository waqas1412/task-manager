package com.taskmanager.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Utility class for date and time operations.
 * Provides parsing and formatting helpers for user input.
 */
public final class DateUtil {
    private static final List<DateTimeFormatter> FORMATTERS = List.of(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        DateTimeFormatter.ISO_LOCAL_DATE
    );

    private static final DateTimeFormatter DISPLAY_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateUtil() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    /**
     * Parse date string using multiple format attempts.
     * 
     * @param dateString the date string to parse
     * @return parsed LocalDateTime
     * @throws IllegalArgumentException if parsing fails
     */
    public static LocalDateTime parse(String dateString) {
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                // Try parsing as LocalDateTime first
                return LocalDateTime.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                // Try next formatter
            }
        }
        
        throw new IllegalArgumentException(
            "Unable to parse date: " + dateString + 
            ". Expected format: yyyy-MM-dd HH:mm or yyyy-MM-dd"
        );
    }

    /**
     * Format LocalDateTime for display.
     * 
     * @param dateTime the date time to format
     * @return formatted string
     */
    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "N/A";
        }
        return dateTime.format(DISPLAY_FORMATTER);
    }

    /**
     * Get relative time description (e.g., "2 hours ago", "in 3 days").
     * 
     * @param dateTime the date time
     * @return relative time string
     */
    public static String getRelativeTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "N/A";
        }

        LocalDateTime now = LocalDateTime.now();
        long hours = java.time.Duration.between(now, dateTime).toHours();

        if (hours < 0) {
            // Past
            long absHours = Math.abs(hours);
            if (absHours < 1) return "just now";
            if (absHours < 24) return absHours + " hours ago";
            long days = absHours / 24;
            return days + " days ago";
        } else {
            // Future
            if (hours < 1) return "soon";
            if (hours < 24) return "in " + hours + " hours";
            long days = hours / 24;
            return "in " + days + " days";
        }
    }
}

