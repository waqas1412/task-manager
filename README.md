# Task Manager

<div align="center">

![Task Manager](https://img.shields.io/badge/Task-Manager-blue?style=for-the-badge&logo=java&logoColor=white)
![Java](https://img.shields.io/badge/Java-25%20LTS-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.6+-red?style=for-the-badge&logo=apache-maven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-5-green?style=for-the-badge&logo=junit5&logoColor=white)

**A modern command-line task management application built with Java 25 LTS, demonstrating best practices in software architecture, design patterns, and clean code principles.**

[![MIT License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen?style=for-the-badge)](CONTRIBUTING.md)

</div>

---

## 🗺️ Table of Contents

- [📦 Quick Start](#-quick-start)
- [🏗️ Architecture](#️-architecture)
- [🚀 Technology Stack](#-technology-stack)
- [🎮 Features](#-features)
- [💻 Commands](#-commands)
- [🛠️ Development](#️-development)
- [🧪 Testing](#-testing)
- [📚 Design Patterns](#-design-patterns)
- [🎯 SOLID Principles](#-solid-principles)
- [📖 Documentation](#-documentation)
- [🤝 Contributing](#-contributing)
- [📄 License](#-license)

---

## 📦 Quick Start

### Prerequisites

- **Java 25 LTS** or higher
- **Maven 3.6+**

### 🚀 One-Command Setup

```bash
# Clone the repository
git clone https://github.com/waqas1412/task-manager.git
cd task-manager

# Build the project
mvn clean package
```

**That's it!** 🎉 The application will:
- ✅ Compile all Java 25 source files
- ✅ Run 31 unit tests
- ✅ Generate executable JAR file
- ✅ Create data directory for persistence

### 🌐 Running the Application

```bash
# Run the JAR file
java -jar target/task-manager-1.0.0.jar

# Or use Maven
mvn exec:java -Dexec.mainClass="com.taskmanager.Main"
```

### 🎯 First Steps

```
task-manager> help              # View all available commands
task-manager> create            # Create your first task
task-manager> list              # View all tasks
task-manager> stats             # See task statistics
```

---

## 🏗️ Architecture

### Clean Architecture Design

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

### 🎯 Layer Responsibilities

| Layer | Responsibility | Key Components |
|-------|----------------|----------------|
| **CLI Layer** | User interaction, command parsing | CommandHandler, OutputFormatter |
| **Service Layer** | Business logic, orchestration | TaskService, CategoryService, SearchService |
| **Repository Layer** | Data persistence, caching | JsonTaskRepository, JsonCategoryRepository |
| **Domain Layer** | Core business entities | Task, Category, Priority, Status |

---

## 🚀 Technology Stack

### Core Technologies

$${\color{#AC3097}Java \space \color{#56565E}25 \space LTS}$$

- **Latest LTS** with modern language features
- **Records** for immutable data classes
- **Enhanced switch** expressions
- **Text blocks** for multi-line strings
- **Pattern matching** capabilities

$${\color{#AC3097}Maven \space \color{#56565E}3.6+}$$

- **Dependency management** with minimal dependencies
- **Build automation** with lifecycle phases
- **Testing integration** with JUnit 5
- **JAR packaging** with manifest configuration

### Libraries & Frameworks

$${\color{#AC3097}Gson \space \color{#56565E}2.11.0}$$

- **JSON serialization** for data persistence
- **Custom type adapters** for LocalDateTime
- **Pretty printing** for readable JSON files

$${\color{#AC3097}JUnit \space \color{#56565E}5.11.0}$$

- **Modern testing** framework
- **Parameterized tests** for multiple scenarios
- **Descriptive test names** with @DisplayName
- **Comprehensive assertions** for validation

### Development Tools

$${\color{#AC3097}Development \space \color{#56565E}Tools}$$

- **Java 25 Compiler** with preview features
- **Maven Surefire** for test execution
- **Git** for version control
- **JSON** for data storage

---

## 🎮 Features

### 🔧 Task Management

$${\color{#AC3097}Task \space \color{#56565E}CRUD \space Operations}$$

- **Create** tasks with title, description, priority, and due date
- **Read** task information with filtering and sorting
- **Update** task status, priority, and details
- **Delete** tasks with confirmation

$${\color{#AC3097}Task \space \color{#56565E}Properties}$$

- **Priorities**: LOW, MEDIUM, HIGH, CRITICAL
- **Statuses**: TODO, IN_PROGRESS, DONE, CANCELLED
- **Due Dates**: With overdue detection
- **Categories**: Organize tasks by category
- **Timestamps**: Creation and update tracking

### 📊 Advanced Features

$${\color{#AC3097}Search \space \color{#56565E}& \space Filter}$$

- **Keyword search** in title and description
- **Filter by status** (TODO, IN_PROGRESS, DONE)
- **Filter by priority** (LOW to CRITICAL)
- **Filter by category** and date range
- **Overdue detection** and alerts

$${\color{#AC3097}Sorting \space \color{#56565E}Strategies}$$

- **Priority sorting** (ascending/descending)
- **Due date sorting** (ascending/descending)
- **Creation date** sorting
- **Title** alphabetical sorting

### 📁 Category System

$${\color{#AC3097}Category \space \color{#56565E}Management}$$

- **Default categories**: Work, Personal, Shopping, Health, Learning
- **Custom categories** with name, description, and color
- **Category assignment** to tasks
- **Category-based filtering**

### 📈 Statistics & Analytics

$${\color{#AC3097}Task \space \color{#56565E}Statistics}$$

- **Total tasks** count
- **Status distribution** (TODO, IN_PROGRESS, DONE)
- **Overdue tasks** tracking
- **Completion rate** percentage

---

## 💻 Commands

### 🔐 Task Management Commands

$${\color{#AC3097}Task \space \color{#56565E}Operations}$$

```bash
create, add, new          # Create a new task
list [filter]             # List all tasks or filter by: todo, progress, done, overdue, high
update, edit              # Update an existing task
delete, remove, rm        # Delete a task
complete, done            # Mark a task as completed
search <keyword>          # Search tasks by keyword
```

### 📱 Category Management

$${\color{#AC3097}Category \space \color{#56565E}Operations}$$

```bash
category list             # List all categories
category create           # Create a new category
```

### 📊 Analytics

$${\color{#AC3097}Statistics \space \color{#56565E}& \space Info}$$

```bash
stats, statistics         # Show task statistics
help, ?                   # Show help message
exit, quit, q             # Exit the application
```

### 🎯 Example Session

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

---

## 🛠️ Development

### 📋 Prerequisites

- **Java 25 LTS** or higher
- **Maven 3.6+**
- **Git** for version control

### 🚀 Local Development Setup

$${\color{#AC3097}Clone \space \color{#56565E}Repository}$$

```bash
# Clone repository
git clone https://github.com/waqas1412/task-manager.git
cd task-manager
```

$${\color{#AC3097}Build \space \color{#56565E}Project}$$

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package JAR
mvn package

# Skip tests during packaging
mvn package -DskipTests
```

### 🎮 Running the Application

$${\color{#AC3097}Development \space \color{#56565E}Mode}$$

```bash
# Run with Maven
mvn exec:java -Dexec.mainClass="com.taskmanager.Main"

# Run JAR file
java -jar target/task-manager-1.0.0.jar

# Run with preview features (if needed)
java --enable-preview -jar target/task-manager-1.0.0.jar
```

### 📁 Project Structure

```
task-manager/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/taskmanager/
│   │   │       ├── cli/                  # Command-line interface
│   │   │       │   ├── CommandHandler.java
│   │   │       │   └── OutputFormatter.java
│   │   │       ├── domain/               # Domain model
│   │   │       │   ├── Task.java
│   │   │       │   ├── Category.java
│   │   │       │   ├── Priority.java
│   │   │       │   ├── Status.java
│   │   │       │   └── exception/
│   │   │       ├── repository/           # Data access layer
│   │   │       │   ├── TaskRepository.java
│   │   │       │   ├── CategoryRepository.java
│   │   │       │   └── impl/
│   │   │       ├── service/              # Business logic layer
│   │   │       │   ├── TaskService.java
│   │   │       │   ├── CategoryService.java
│   │   │       │   └── SearchService.java
│   │   │       ├── util/                 # Utilities
│   │   │       │   ├── JsonUtil.java
│   │   │       │   └── DateUtil.java
│   │   │       └── Main.java
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/taskmanager/          # Unit tests
│               ├── TaskTest.java
│               ├── PriorityTest.java
│               └── StatusTest.java
├── data/                                  # JSON data storage
│   ├── tasks.json
│   └── categories.json
├── pom.xml                                # Maven configuration
└── README.md
```

---

## 🧪 Testing

### 🎯 Test Commands

$${\color{#AC3097}Run \space \color{#56565E}Tests}$$

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TaskTest

# Run tests with coverage
mvn test jacoco:report

# Run tests in verbose mode
mvn test -X
```

### 📋 Test Coverage

$${\color{#AC3097}Test \space \color{#56565E}Statistics}$$

- **Total Tests**: 31 unit tests
- **Test Classes**: 3 (TaskTest, PriorityTest, StatusTest)
- **Pass Rate**: 100%
- **Coverage**: Domain model and business logic

$${\color{#AC3097}Test \space \color{#56565E}Types}$$

- **Unit Tests**: Individual component testing
- **Parameterized Tests**: Multiple scenario testing
- **Validation Tests**: Input validation testing
- **State Transition Tests**: Status change validation

### 🎯 Example Tests

```java
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
    assertEquals(Priority.HIGH, task.getPriority());
}

@ParameterizedTest
@CsvSource({
    "LOW, LOW",
    "MEDIUM, MEDIUM",
    "HIGH, HIGH",
    "CRITICAL, CRITICAL"
})
@DisplayName("Should parse priority from string")
void shouldParsePriorityFromString(String input, Priority expected) {
    assertEquals(expected, Priority.fromString(input));
}
```

---

## 📚 Design Patterns

### 🎨 Implemented Patterns

$${\color{#AC3097}Repository \space \color{#56565E}Pattern}$$

- **Purpose**: Abstract data access layer
- **Implementation**: TaskRepository, CategoryRepository interfaces
- **Benefits**: Testability, flexibility, separation of concerns

$${\color{#AC3097}Service \space \color{#56565E}Layer \space Pattern}$$

- **Purpose**: Encapsulate business logic
- **Implementation**: TaskService, CategoryService, SearchService
- **Benefits**: Single responsibility, reusability

$${\color{#AC3097}Builder \space \color{#56565E}Pattern}$$

- **Purpose**: Flexible object construction
- **Implementation**: Task.Builder
- **Benefits**: Immutability, validation, readability

$${\color{#AC3097}Strategy \space \color{#56565E}Pattern}$$

- **Purpose**: Pluggable algorithms
- **Implementation**: SortStrategy enum
- **Benefits**: Open/closed principle, flexibility

$${\color{#AC3097}Singleton \space \color{#56565E}Pattern}$$

- **Purpose**: Single instance management
- **Implementation**: Repository instances
- **Benefits**: Resource management, consistency

$${\color{#AC3097}Command \space \color{#56565E}Pattern}$$

- **Purpose**: Encapsulate requests
- **Implementation**: CLI command handling
- **Benefits**: Extensibility, undo/redo support

$${\color{#AC3097}Factory \space \color{#56565E}Pattern}$$

- **Purpose**: Object creation abstraction
- **Implementation**: Category.create(), Task.Builder
- **Benefits**: Encapsulation, validation

---

## 🎯 SOLID Principles

### 📐 Principle Applications

$${\color{#AC3097}Single \space \color{#56565E}Responsibility \space Principle}$$

- **Each class has one reason to change**
- OutputFormatter only handles formatting
- Services handle specific business logic
- Repositories only manage data access

$${\color{#AC3097}Open/Closed \space \color{#56565E}Principle}$$

- **Open for extension, closed for modification**
- New repository implementations can be added
- New sorting strategies via enum
- New commands without changing existing code

$${\color{#AC3097}Liskov \space \color{#56565E}Substitution \space Principle}$$

- **Subtypes must be substitutable for base types**
- Repository implementations are interchangeable
- Service layer depends on interfaces
- Polymorphic behavior maintained

$${\color{#AC3097}Interface \space \color{#56565E}Segregation \space Principle}$$

- **Clients shouldn't depend on unused methods**
- Focused, minimal interfaces
- TaskRepository and CategoryRepository are separate
- No fat interfaces

$${\color{#AC3097}Dependency \space \color{#56565E}Inversion \space Principle}$$

- **Depend on abstractions, not concretions**
- Services depend on repository interfaces
- High-level modules independent of low-level
- Dependency injection support

---

## 📖 Documentation

### 📚 Java 25 Features Demonstrated

$${\color{#AC3097}Records \space \color{#56565E}(Java \space 14+)}$$

```java
public record Category(
    String id,
    String name,
    String description,
    String color
) {
    // Compact constructor for validation
    public Category {
        Objects.requireNonNull(name, "Name cannot be null");
    }
}
```

$${\color{#AC3097}Enhanced \space \color{#56565E}Switch \space Expressions}$$

```java
public static Priority fromString(String value) {
    return switch (value.toUpperCase()) {
        case "LOW", "L", "1" -> LOW;
        case "MEDIUM", "M", "2" -> MEDIUM;
        case "HIGH", "H", "3" -> HIGH;
        case "CRITICAL", "C", "4" -> CRITICAL;
        default -> throw new IllegalArgumentException("Invalid priority");
    };
}
```

$${\color{#AC3097}Text \space \color{#56565E}Blocks}$$

```java
private static final String BANNER = """
    ╔════════════════════════════════════════╗
    ║        TASK MANAGER v1.0.0             ║
    ║   A Modern Java 25 LTS Application     ║
    ╚════════════════════════════════════════╝
    """;
```

$${\color{#AC3097}Stream \space \color{#56565E}API}$$

```java
public List<Task> searchByKeyword(String keyword) {
    return taskRepository.findAll().stream()
        .filter(task -> 
            task.getTitle().toLowerCase().contains(keyword) ||
            task.getDescription().toLowerCase().contains(keyword)
        )
        .collect(Collectors.toList());
}
```

### 🎯 Key Learning Demonstrations

This project showcases:

1. **Modern Java Development**: Java 25 LTS features
2. **Clean Architecture**: Layered design with clear boundaries
3. **Design Patterns**: 7 GoF patterns implemented
4. **SOLID Principles**: All 5 principles applied
5. **Testing**: Comprehensive unit tests with JUnit 5
6. **Build Tools**: Maven dependency and lifecycle management
7. **Version Control**: 50 incremental commits
8. **Documentation**: JavaDoc and README
9. **Code Quality**: Clean, readable, maintainable
10. **Best Practices**: Industry-standard development

---

## 🤝 Contributing

### 🚀 How to Contribute

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### 📋 Development Guidelines

- Follow **Java** coding conventions
- Write **comprehensive tests**
- Update **documentation**
- Follow **conventional commits**
- Ensure **code quality** with proper formatting

### 🐛 Bug Reports

Please use the [GitHub Issues](https://github.com/waqas1412/task-manager/issues) to report bugs.

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 👤 Author

**waqas1412**

<div align="center">

**Built with ❤️ using Java 25 LTS, Maven, and best practices**

[![GitHub](https://img.shields.io/badge/GitHub-waqas1412-black?style=for-the-badge&logo=github)](https://github.com/waqas1412)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue?style=for-the-badge&logo=linkedin)](https://linkedin.com/in/waqas1412)

---

⭐ **Star this repository if you found it helpful!**

</div>

