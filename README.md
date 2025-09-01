# Task Manager

A modern command-line task management application built with **Java 25 LTS**, demonstrating best practices in software architecture, design patterns, and clean code principles.

## Features

- **Task Management**: Create, update, delete, and organize tasks with priorities and due dates
- **Category System**: Organize tasks into customizable categories
- **Advanced Search**: Filter and search tasks by status, priority, category, or keywords
- **Smart Sorting**: Multiple sorting strategies (priority, due date, creation date, title)
- **Statistics Dashboard**: Track completion rates and task distribution
- **Persistent Storage**: JSON-based data persistence
- **Interactive CLI**: User-friendly command-line interface with helpful prompts

## Architecture

This project follows **Clean Architecture** principles with clear separation of concerns:

```
┌─────────────────────────────────────┐
│         CLI Interface Layer         │  ← User interaction
│    (Commands, Input Validation)     │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│         Service Layer               │  ← Business logic
│  (TaskService, CategoryService)     │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│       Repository Layer              │  ← Data access
│    (TaskRepository, etc.)           │
└─────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────┐
│         Domain Model                │  ← Core entities
│   (Task, Category, Priority)        │
└─────────────────────────────────────┘
```

## Java 25 Features Demonstrated

- **Records**: Immutable data classes for DTOs and value objects (`Category`, `TaskStatistics`)
- **Sealed Classes**: Type-safe enumerations (`Priority`, `Status`)
- **Pattern Matching**: Enhanced switch expressions for command handling
- **Text Blocks**: Multi-line strings for help messages and banners
- **Builder Pattern**: Flexible object construction for complex entities
- **Stream API**: Functional programming for filtering and transforming collections

## Design Patterns

1. **Repository Pattern**: Abstract data access layer
2. **Service Layer Pattern**: Encapsulate business logic
3. **Builder Pattern**: Flexible task creation
4. **Strategy Pattern**: Pluggable sorting algorithms
5. **Singleton Pattern**: Repository instances
6. **Command Pattern**: CLI command handling
7. **Factory Pattern**: Object creation with validation

## SOLID Principles

- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed**: Extensible through interfaces
- **Liskov Substitution**: Repository implementations are interchangeable
- **Interface Segregation**: Focused, minimal interfaces
- **Dependency Inversion**: Depend on abstractions, not implementations

## Prerequisites

- Java 25 LTS or higher
- Maven 3.6+

## Building

```bash
mvn clean package
```

## Running

```bash
java -jar target/task-manager-1.0.0.jar
```

Or with Maven:

```bash
mvn exec:java -Dexec.mainClass="com.taskmanager.Main"
```

## Usage

### Available Commands

#### Task Management
- `create`, `add`, `new` - Create a new task
- `list [filter]` - List tasks (filters: todo, progress, done, overdue, high)
- `update`, `edit` - Update an existing task
- `delete`, `remove`, `rm` - Delete a task
- `complete`, `done` - Mark a task as completed
- `search <keyword>` - Search tasks by keyword

#### Category Management
- `category list` - List all categories
- `category create` - Create a new category

#### Other
- `stats`, `statistics` - Show task statistics
- `help`, `?` - Show help message
- `exit`, `quit`, `q` - Exit the application

### Example Session

```
task-manager> create
Title: Implement user authentication
Description: Add JWT-based authentication to the API
Priority: HIGH
Category: Work
Due date: 2025-10-25 17:00
✓ Task created successfully!

task-manager> list
╔════════════════════════════════════════════════════════════════════════════╗
  All Tasks (1 tasks)
╔════════════════════════════════════════════════════════════════════════════╗
  #    Title                          Status       Priority   Due Date
────────────────────────────────────────────────────────────────────────────
  1    Implement user authentication  ○ To Do      ↑ High     2025-10-25 17:00
╔════════════════════════════════════════════════════════════════════════════╗

task-manager> stats
╔════════════════════════════════════════════════════════════════════════════╗
  Task Statistics
╔════════════════════════════════════════════════════════════════════════════╗
  Total Tasks:      1
  TODO:             1
  In Progress:      0
  Done:             0
  Overdue:          0
  Completion Rate:  0.0%
╔════════════════════════════════════════════════════════════════════════════╗
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/taskmanager/
│   │       ├── cli/                  # Command-line interface
│   │       │   ├── CommandHandler.java
│   │       │   └── OutputFormatter.java
│   │       ├── domain/               # Domain model
│   │       │   ├── Task.java
│   │       │   ├── Category.java
│   │       │   ├── Priority.java
│   │       │   ├── Status.java
│   │       │   └── exception/
│   │       ├── repository/           # Data access layer
│   │       │   ├── TaskRepository.java
│   │       │   ├── CategoryRepository.java
│   │       │   └── impl/
│   │       ├── service/              # Business logic layer
│   │       │   ├── TaskService.java
│   │       │   ├── CategoryService.java
│   │       │   └── SearchService.java
│   │       ├── util/                 # Utilities
│   │       │   ├── JsonUtil.java
│   │       │   └── DateUtil.java
│   │       └── Main.java
│   └── resources/
└── test/
    └── java/
        └── com/taskmanager/          # Unit tests
```

## Data Storage

Tasks and categories are stored in JSON format in the `data/` directory:
- `data/tasks.json` - Task data
- `data/categories.json` - Category data

Default categories are created on first run:
- Work
- Personal
- Shopping
- Health
- Learning

## Testing

Run unit tests:

```bash
mvn test
```

## Best Practices Demonstrated

1. **Immutability**: Domain objects use immutable design
2. **Validation**: Input validation at multiple layers
3. **Error Handling**: Custom exceptions with meaningful messages
4. **Documentation**: Comprehensive JavaDoc comments
5. **Code Organization**: Clear package structure
6. **Naming Conventions**: Descriptive, intention-revealing names
7. **DRY Principle**: No code duplication
8. **Separation of Concerns**: Each layer has distinct responsibilities

## License

MIT License

## Author

Developer - Demonstrating modern Java development practices

