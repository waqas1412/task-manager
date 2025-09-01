package com.taskmanager.service;

import com.taskmanager.domain.Priority;
import com.taskmanager.domain.Status;
import com.taskmanager.domain.Task;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.impl.JsonTaskRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service for searching, filtering, and sorting tasks.
 * Demonstrates Strategy pattern for different sorting strategies.
 */
public class SearchService {
    private final TaskRepository taskRepository;

    public SearchService() {
        this.taskRepository = JsonTaskRepository.getInstance();
    }

    public SearchService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Search tasks by keyword in title or description.
     * 
     * @param keyword search keyword
     * @return matching tasks
     */
    public List<Task> searchByKeyword(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return taskRepository.findAll().stream()
            .filter(task -> 
                task.getTitle().toLowerCase().contains(lowerKeyword) ||
                task.getDescription().toLowerCase().contains(lowerKeyword)
            )
            .collect(Collectors.toList());
    }

    /**
     * Filter tasks by status.
     */
    public List<Task> filterByStatus(Status status) {
        return taskRepository.findAll().stream()
            .filter(task -> task.getStatus() == status)
            .collect(Collectors.toList());
    }

    /**
     * Filter tasks by priority.
     */
    public List<Task> filterByPriority(Priority priority) {
        return taskRepository.findAll().stream()
            .filter(task -> task.getPriority() == priority)
            .collect(Collectors.toList());
    }

    /**
     * Filter tasks by category.
     */
    public List<Task> filterByCategory(String categoryId) {
        return taskRepository.findByCategoryId(categoryId);
    }

    /**
     * Get overdue tasks.
     */
    public List<Task> getOverdueTasks() {
        return taskRepository.findAll().stream()
            .filter(Task::isOverdue)
            .collect(Collectors.toList());
    }

    /**
     * Get tasks due soon (within 24 hours).
     */
    public List<Task> getTasksDueSoon() {
        return taskRepository.findAll().stream()
            .filter(Task::isDueSoon)
            .collect(Collectors.toList());
    }

    /**
     * Filter tasks by date range.
     */
    public List<Task> filterByDateRange(LocalDateTime start, LocalDateTime end) {
        return taskRepository.findAll().stream()
            .filter(task -> {
                LocalDateTime dueDate = task.getDueDate();
                return dueDate != null && 
                       !dueDate.isBefore(start) && 
                       !dueDate.isAfter(end);
            })
            .collect(Collectors.toList());
    }

    /**
     * Advanced filter with multiple criteria.
     * Demonstrates functional programming with predicates.
     */
    public List<Task> filter(TaskFilter filter) {
        Predicate<Task> predicate = task -> true;

        if (filter.status() != null) {
            predicate = predicate.and(task -> task.getStatus() == filter.status());
        }

        if (filter.priority() != null) {
            predicate = predicate.and(task -> task.getPriority() == filter.priority());
        }

        if (filter.categoryId() != null) {
            predicate = predicate.and(task -> 
                filter.categoryId().equals(task.getCategoryId())
            );
        }

        if (filter.keyword() != null && !filter.keyword().isBlank()) {
            String lowerKeyword = filter.keyword().toLowerCase();
            predicate = predicate.and(task ->
                task.getTitle().toLowerCase().contains(lowerKeyword) ||
                task.getDescription().toLowerCase().contains(lowerKeyword)
            );
        }

        if (filter.overdueOnly()) {
            predicate = predicate.and(Task::isOverdue);
        }

        return taskRepository.findAll().stream()
            .filter(predicate)
            .collect(Collectors.toList());
    }

    /**
     * Sort tasks using specified strategy.
     * Demonstrates Strategy pattern.
     */
    public List<Task> sort(List<Task> tasks, SortStrategy strategy) {
        return tasks.stream()
            .sorted(strategy.getComparator())
            .collect(Collectors.toList());
    }

    /**
     * Record for filter criteria.
     */
    public record TaskFilter(
        Status status,
        Priority priority,
        String categoryId,
        String keyword,
        boolean overdueOnly
    ) {
        public TaskFilter {
            if (keyword == null) {
                keyword = "";
            }
        }
    }

    /**
     * Enum for sort strategies.
     * Demonstrates Strategy pattern with enum.
     */
    public enum SortStrategy {
        PRIORITY_DESC(Comparator.comparing(Task::getPriority, 
            Comparator.comparingInt(Priority::getLevel).reversed())),
        PRIORITY_ASC(Comparator.comparing(Task::getPriority, 
            Comparator.comparingInt(Priority::getLevel))),
        DUE_DATE_ASC(Comparator.comparing(Task::getDueDate, 
            Comparator.nullsLast(Comparator.naturalOrder()))),
        DUE_DATE_DESC(Comparator.comparing(Task::getDueDate, 
            Comparator.nullsLast(Comparator.reverseOrder()))),
        CREATED_ASC(Comparator.comparing(Task::getCreatedAt)),
        CREATED_DESC(Comparator.comparing(Task::getCreatedAt).reversed()),
        TITLE_ASC(Comparator.comparing(Task::getTitle)),
        TITLE_DESC(Comparator.comparing(Task::getTitle).reversed());

        private final Comparator<Task> comparator;

        SortStrategy(Comparator<Task> comparator) {
            this.comparator = comparator;
        }

        public Comparator<Task> getComparator() {
            return comparator;
        }
    }
}

