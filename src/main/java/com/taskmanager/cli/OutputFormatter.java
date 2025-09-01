package com.taskmanager.cli;

import com.taskmanager.domain.Category;
import com.taskmanager.domain.Task;
import com.taskmanager.service.CategoryService;
import com.taskmanager.service.TaskService;
import com.taskmanager.util.DateUtil;

import java.util.List;

/**
 * Formats output for CLI display.
 * Demonstrates Single Responsibility Principle - handles only formatting.
 */
public class OutputFormatter {
    private static final String BORDER = "═".repeat(80);
    private static final String LINE = "─".repeat(80);

    /**
     * Print a single task with details.
     */
    public void printTask(Task task, CategoryService categoryService) {
        System.out.println("\n" + BORDER);
        System.out.println("  " + task.getTitle());
        System.out.println(LINE);
        
        if (!task.getDescription().isEmpty()) {
            System.out.println("  Description: " + task.getDescription());
        }
        
        System.out.println("  Status:      " + getStatusIcon(task.getStatus()) + " " + task.getStatus());
        System.out.println("  Priority:    " + getPriorityIcon(task.getPriority()) + " " + task.getPriority());
        
        if (task.getCategoryId() != null) {
            try {
                Category category = categoryService.getCategory(task.getCategoryId());
                System.out.println("  Category:    " + category.name());
            } catch (Exception e) {
                // Category not found
            }
        }
        
        if (task.getDueDate() != null) {
            String dueInfo = DateUtil.format(task.getDueDate()) + " (" + DateUtil.getRelativeTime(task.getDueDate()) + ")";
            if (task.isOverdue()) {
                dueInfo += " ⚠ OVERDUE";
            }
            System.out.println("  Due Date:    " + dueInfo);
        }
        
        System.out.println("  Created:     " + DateUtil.format(task.getCreatedAt()));
        System.out.println(BORDER);
    }

    /**
     * Print a list of tasks in table format.
     */
    public void printTaskList(List<Task> tasks, CategoryService categoryService, String header) {
        System.out.println("\n" + BORDER);
        System.out.println("  " + header + " (" + tasks.size() + " tasks)");
        System.out.println(BORDER);

        if (tasks.isEmpty()) {
            System.out.println("  No tasks found.");
            System.out.println(BORDER);
            return;
        }

        System.out.printf("  %-4s %-30s %-12s %-10s %-15s%n", "#", "Title", "Status", "Priority", "Due Date");
        System.out.println(LINE);

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String title = truncate(task.getTitle(), 30);
            String status = getStatusIcon(task.getStatus()) + " " + task.getStatus().toString();
            String priority = getPriorityIcon(task.getPriority()) + " " + task.getPriority().toString();
            String dueDate = task.getDueDate() != null ? DateUtil.format(task.getDueDate()) : "N/A";
            
            if (task.isOverdue()) {
                dueDate += " ⚠";
            }

            System.out.printf("  %-4d %-30s %-12s %-10s %-15s%n", 
                i + 1, title, status, priority, dueDate);
        }

        System.out.println(BORDER);
    }

    /**
     * Print category list.
     */
    public void printCategoryList(List<Category> categories) {
        System.out.println("\n" + BORDER);
        System.out.println("  Categories (" + categories.size() + ")");
        System.out.println(BORDER);

        if (categories.isEmpty()) {
            System.out.println("  No categories found.");
            System.out.println(BORDER);
            return;
        }

        System.out.printf("  %-20s %-40s %-10s%n", "Name", "Description", "Color");
        System.out.println(LINE);

        for (Category category : categories) {
            String name = truncate(category.name(), 20);
            String description = truncate(category.description(), 40);
            System.out.printf("  %-20s %-40s %-10s%n", name, description, category.color());
        }

        System.out.println(BORDER);
    }

    /**
     * Print task statistics.
     */
    public void printStatistics(TaskService.TaskStatistics stats) {
        System.out.println("\n" + BORDER);
        System.out.println("  Task Statistics");
        System.out.println(BORDER);
        System.out.println("  Total Tasks:      " + stats.total());
        System.out.println("  TODO:             " + stats.todo());
        System.out.println("  In Progress:      " + stats.inProgress());
        System.out.println("  Done:             " + stats.done());
        System.out.println("  Overdue:          " + stats.overdue());
        
        if (stats.total() > 0) {
            double completionRate = (stats.done() * 100.0) / stats.total();
            System.out.printf("  Completion Rate:  %.1f%%%n", completionRate);
        }
        
        System.out.println(BORDER);
    }

    /**
     * Get icon for status.
     */
    private String getStatusIcon(com.taskmanager.domain.Status status) {
        return switch (status) {
            case TODO -> "○";
            case IN_PROGRESS -> "◐";
            case DONE -> "●";
            case CANCELLED -> "✕";
        };
    }

    /**
     * Get icon for priority.
     */
    private String getPriorityIcon(com.taskmanager.domain.Priority priority) {
        return switch (priority) {
            case LOW -> "↓";
            case MEDIUM -> "→";
            case HIGH -> "↑";
            case CRITICAL -> "⚡";
        };
    }

    /**
     * Truncate string to specified length.
     */
    private String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}

