package com.taskmanager.cli;

import com.taskmanager.domain.Category;
import com.taskmanager.domain.Priority;
import com.taskmanager.domain.Status;
import com.taskmanager.domain.Task;
import com.taskmanager.service.CategoryService;
import com.taskmanager.service.SearchService;
import com.taskmanager.service.TaskService;
import com.taskmanager.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Handles CLI commands and user interaction.
 * Demonstrates Command pattern for handling different user commands.
 */
public class CommandHandler {
    private final TaskService taskService;
    private final CategoryService categoryService;
    private final SearchService searchService;
    private final Scanner scanner;
    private final OutputFormatter formatter;

    public CommandHandler() {
        this.taskService = new TaskService();
        this.categoryService = new CategoryService();
        this.searchService = new SearchService();
        this.scanner = new Scanner(System.in);
        this.formatter = new OutputFormatter();
    }

    /**
     * Main command processing loop.
     * Uses pattern matching in switch (Java 25 feature).
     */
    public void processCommand(String input) {
        String[] parts = input.trim().split("\\s+", 2);
        String command = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";

        try {
            switch (command) {
                case "create", "add", "new" -> handleCreateTask();
                case "list", "ls", "show" -> handleListTasks(args);
                case "update", "edit" -> handleUpdateTask();
                case "delete", "remove", "rm" -> handleDeleteTask();
                case "complete", "done" -> handleCompleteTask();
                case "search", "find" -> handleSearch(args);
                case "category", "cat" -> handleCategory(args);
                case "stats", "statistics" -> handleStatistics();
                case "help", "?" -> handleHelp();
                case "exit", "quit", "q" -> System.exit(0);
                default -> System.out.println("Unknown command. Type 'help' for available commands.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleCreateTask() {
        System.out.println("\n=== Create New Task ===");
        
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Description (optional): ");
        String description = scanner.nextLine().trim();
        
        System.out.print("Priority (LOW/MEDIUM/HIGH/CRITICAL) [MEDIUM]: ");
        String priorityInput = scanner.nextLine().trim();
        Priority priority = priorityInput.isEmpty() ? Priority.MEDIUM : Priority.fromString(priorityInput);
        
        System.out.print("Category (name or leave empty): ");
        String categoryInput = scanner.nextLine().trim();
        String categoryId = null;
        if (!categoryInput.isEmpty()) {
            try {
                Category category = categoryService.getCategoryByName(categoryInput);
                categoryId = category.id();
            } catch (Exception e) {
                System.out.println("Warning: Category not found. Task will have no category.");
            }
        }
        
        System.out.print("Due date (yyyy-MM-dd HH:mm or yyyy-MM-dd, optional): ");
        String dueDateInput = scanner.nextLine().trim();
        LocalDateTime dueDate = null;
        if (!dueDateInput.isEmpty()) {
            try {
                dueDate = DateUtil.parse(dueDateInput);
            } catch (Exception e) {
                System.out.println("Warning: Invalid date format. Task will have no due date.");
            }
        }
        
        Task task = taskService.createTask(title, description, priority, categoryId, dueDate);
        System.out.println("\n✓ Task created successfully!");
        formatter.printTask(task, categoryService);
    }

    private void handleListTasks(String args) {
        List<Task> tasks;
        String header = "All Tasks";

        if (args.isEmpty()) {
            tasks = taskService.getAllTasks();
        } else {
            String[] parts = args.split("\\s+", 2);
            String filterType = parts[0].toLowerCase();
            
            tasks = switch (filterType) {
                case "todo" -> {
                    header = "TODO Tasks";
                    yield searchService.filterByStatus(Status.TODO);
                }
                case "progress", "inprogress" -> {
                    header = "In Progress Tasks";
                    yield searchService.filterByStatus(Status.IN_PROGRESS);
                }
                case "done", "completed" -> {
                    header = "Completed Tasks";
                    yield searchService.filterByStatus(Status.DONE);
                }
                case "overdue" -> {
                    header = "Overdue Tasks";
                    yield searchService.getOverdueTasks();
                }
                case "high", "critical" -> {
                    header = "High Priority Tasks";
                    yield searchService.filterByPriority(Priority.HIGH);
                }
                default -> taskService.getAllTasks();
            };
        }

        tasks = searchService.sort(tasks, SearchService.SortStrategy.PRIORITY_DESC);
        formatter.printTaskList(tasks, categoryService, header);
    }

    private void handleUpdateTask() {
        System.out.print("Enter task number to update: ");
        String taskNum = scanner.nextLine().trim();
        
        List<Task> tasks = taskService.getAllTasks();
        if (taskNum.matches("\\d+")) {
            int index = Integer.parseInt(taskNum) - 1;
            if (index >= 0 && index < tasks.size()) {
                Task task = tasks.get(index);
                updateTaskMenu(task);
                return;
            }
        }
        System.out.println("Invalid task number.");
    }

    private void updateTaskMenu(Task task) {
        System.out.println("\n=== Update Task ===");
        formatter.printTask(task, categoryService);
        
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Status");
        System.out.println("2. Priority");
        System.out.println("3. Title");
        System.out.println("4. Description");
        System.out.println("5. Due Date");
        System.out.print("Choice: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1" -> {
                System.out.print("New status (TODO/IN_PROGRESS/DONE/CANCELLED): ");
                Status status = Status.fromString(scanner.nextLine().trim());
                taskService.updateTaskStatus(task.getId(), status);
                System.out.println("✓ Status updated!");
            }
            case "2" -> {
                System.out.print("New priority (LOW/MEDIUM/HIGH/CRITICAL): ");
                Priority priority = Priority.fromString(scanner.nextLine().trim());
                taskService.updateTaskPriority(task.getId(), priority);
                System.out.println("✓ Priority updated!");
            }
            case "3" -> {
                System.out.print("New title: ");
                String title = scanner.nextLine().trim();
                taskService.updateTaskTitle(task.getId(), title);
                System.out.println("✓ Title updated!");
            }
            case "4" -> {
                System.out.print("New description: ");
                String description = scanner.nextLine().trim();
                taskService.updateTaskDescription(task.getId(), description);
                System.out.println("✓ Description updated!");
            }
            case "5" -> {
                System.out.print("New due date (yyyy-MM-dd HH:mm): ");
                LocalDateTime dueDate = DateUtil.parse(scanner.nextLine().trim());
                taskService.updateTaskDueDate(task.getId(), dueDate);
                System.out.println("✓ Due date updated!");
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private void handleDeleteTask() {
        System.out.print("Enter task number to delete: ");
        String taskNum = scanner.nextLine().trim();
        
        List<Task> tasks = taskService.getAllTasks();
        if (taskNum.matches("\\d+")) {
            int index = Integer.parseInt(taskNum) - 1;
            if (index >= 0 && index < tasks.size()) {
                Task task = tasks.get(index);
                System.out.print("Delete task '" + task.getTitle() + "'? (y/n): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    taskService.deleteTask(task.getId());
                    System.out.println("✓ Task deleted!");
                }
                return;
            }
        }
        System.out.println("Invalid task number.");
    }

    private void handleCompleteTask() {
        System.out.print("Enter task number to mark as done: ");
        String taskNum = scanner.nextLine().trim();
        
        List<Task> tasks = taskService.getAllTasks();
        if (taskNum.matches("\\d+")) {
            int index = Integer.parseInt(taskNum) - 1;
            if (index >= 0 && index < tasks.size()) {
                Task task = tasks.get(index);
                taskService.updateTaskStatus(task.getId(), Status.DONE);
                System.out.println("✓ Task marked as done!");
                return;
            }
        }
        System.out.println("Invalid task number.");
    }

    private void handleSearch(String keyword) {
        if (keyword.isEmpty()) {
            System.out.print("Enter search keyword: ");
            keyword = scanner.nextLine().trim();
        }
        
        List<Task> results = searchService.searchByKeyword(keyword);
        formatter.printTaskList(results, categoryService, "Search Results for: " + keyword);
    }

    private void handleCategory(String args) {
        if (args.isEmpty() || args.equals("list")) {
            List<Category> categories = categoryService.getAllCategories();
            formatter.printCategoryList(categories);
        } else if (args.startsWith("create") || args.startsWith("add")) {
            handleCreateCategory();
        }
    }

    private void handleCreateCategory() {
        System.out.println("\n=== Create New Category ===");
        
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Description (optional): ");
        String description = scanner.nextLine().trim();
        
        System.out.print("Color (hex code, optional): ");
        String color = scanner.nextLine().trim();
        
        Category category = categoryService.createCategory(name, description, color.isEmpty() ? null : color);
        System.out.println("✓ Category created: " + category.name());
    }

    private void handleStatistics() {
        TaskService.TaskStatistics stats = taskService.getStatistics();
        formatter.printStatistics(stats);
    }

    private void handleHelp() {
        System.out.println("""
            
            === Task Manager - Available Commands ===
            
            Task Management:
              create, add, new          Create a new task
              list [filter]             List all tasks or filter by: todo, progress, done, overdue, high
              update, edit              Update an existing task
              delete, remove, rm        Delete a task
              complete, done            Mark a task as completed
              search <keyword>          Search tasks by keyword
            
            Category Management:
              category list             List all categories
              category create           Create a new category
            
            Other:
              stats, statistics         Show task statistics
              help, ?                   Show this help message
              exit, quit, q             Exit the application
            
            """);
    }
}

