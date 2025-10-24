# Task Manager

<div align="center">

![Java](https://img.shields.io/badge/Java-25%20LTS-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.6+-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**A modern CLI task management application showcasing Java 25 LTS features, clean architecture, and design patterns**

[Features](#-features) • [Quick Start](#-quick-start) • [Architecture](#-architecture) • [Commands](#-commands) • [Documentation](#-documentation)

</div>

---

## 📦 Quick Start

```bash
# Clone and build
git clone https://github.com/waqas1412/task-manager.git
cd task-manager
mvn clean package

# Run
java -jar target/task-manager-1.0.0.jar
```

## ✨ Features

| Category | Features |
|----------|----------|
| **Task Management** | Create, update, delete tasks with priorities (LOW → CRITICAL) and due dates |
| **Organization** | 5 default categories (Work, Personal, Shopping, Health, Learning) + custom categories |
| **Search & Filter** | Keyword search, filter by status/priority/category, overdue detection |
| **Sorting** | By priority, due date, creation date, or title (ascending/descending) |
| **Statistics** | Task counts, completion rate, status distribution |
| **Persistence** | JSON-based storage with auto-save |

## 🏗️ Architecture

<table>
<tr>
<td width="50%">

### Layer Structure

```
┌─────────────────────┐
│    CLI Layer        │ Commands & Display
├─────────────────────┤
│  Service Layer      │ Business Logic
├─────────────────────┤
│ Repository Layer    │ Data Access
├─────────────────────┤
│   Domain Model      │ Core Entities
└─────────────────────┘
```

</td>
<td width="50%">

### Components

| Layer | Classes |
|-------|---------|
| **CLI** | CommandHandler, OutputFormatter |
| **Service** | TaskService, CategoryService, SearchService |
| **Repository** | JsonTaskRepository, JsonCategoryRepository |
| **Domain** | Task, Category, Priority, Status |

</td>
</tr>
</table>

## 🚀 Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 25 LTS | Core language with modern features (Records, Switch expressions, Text blocks) |
| **Maven** | 3.6+ | Build automation and dependency management |
| **Gson** | 2.11.0 | JSON serialization for data persistence |
| **JUnit 5** | 5.11.0 | Unit testing framework (31 tests, 100% pass rate) |

## 💻 Commands

<table>
<tr>
<td width="50%">

### Task Operations
```bash
create, add          # Create task
list [filter]        # List tasks
  ├─ todo           # TODO tasks
  ├─ progress       # In progress
  ├─ done           # Completed
  ├─ overdue        # Overdue tasks
  └─ high           # High priority
update, edit         # Update task
delete, rm           # Delete task
complete, done       # Mark as done
search <keyword>     # Search tasks
```

</td>
<td width="50%">

### Other Commands
```bash
category list        # List categories
category create      # New category
stats                # Statistics
help                 # Show help
exit, quit           # Exit app
```

### Example
```
task-manager> create
Title: Fix bug #123
Priority: HIGH
Category: Work
✓ Task created!

task-manager> list
#  Title         Status    Priority
1  Fix bug #123  ○ To Do   ↑ High
```

</td>
</tr>
</table>

## 📊 Design Patterns & Principles

<table>
<tr>
<td width="50%">

### Design Patterns (7)

| Pattern | Implementation |
|---------|----------------|
| **Repository** | Data access abstraction |
| **Service Layer** | Business logic separation |
| **Builder** | Task construction |
| **Strategy** | Sorting algorithms |
| **Singleton** | Repository instances |
| **Command** | CLI command handling |
| **Factory** | Object creation |

</td>
<td width="50%">

### SOLID Principles

| Principle | Application |
|-----------|-------------|
| **S**ingle Responsibility | Each class has one purpose |
| **O**pen/Closed | Extensible via interfaces |
| **L**iskov Substitution | Repository interchangeability |
| **I**nterface Segregation | Focused interfaces |
| **D**ependency Inversion | Depend on abstractions |

</td>
</tr>
</table>

## 🎯 Java 25 Features

| Feature | Usage | Example |
|---------|-------|---------|
| **Records** | Immutable DTOs | `record Category(String id, String name, ...)` |
| **Enhanced Switch** | Pattern matching | `switch (status) { case TODO -> ...; }` |
| **Text Blocks** | Multi-line strings | `"""Welcome Banner"""` |
| **Stream API** | Functional operations | `tasks.stream().filter(...).collect(...)` |
| **Builder Pattern** | Flexible construction | `new Task.Builder().title("...").build()` |

## 🧪 Testing

```bash
mvn test                    # Run all tests
mvn test -Dtest=TaskTest   # Run specific test
```

| Metric | Value |
|--------|-------|
| **Total Tests** | 31 |
| **Test Classes** | 3 (TaskTest, PriorityTest, StatusTest) |
| **Pass Rate** | 100% |
| **Coverage** | Domain model + Business logic |

**Test Types**: Unit tests, Parameterized tests, Validation tests, State transition tests

## 📁 Project Structure

```
task-manager/
├── src/main/java/com/taskmanager/
│   ├── cli/                    # CommandHandler, OutputFormatter
│   ├── domain/                 # Task, Category, Priority, Status
│   │   └── exception/          # Custom exceptions
│   ├── repository/             # TaskRepository, CategoryRepository
│   │   └── impl/               # JSON implementations
│   ├── service/                # TaskService, CategoryService, SearchService
│   ├── util/                   # JsonUtil, DateUtil
│   └── Main.java
├── src/test/java/              # Unit tests
├── data/                       # JSON storage (tasks.json, categories.json)
└── pom.xml                     # Maven configuration
```

## 🛠️ Development

### Build & Run

```bash
# Compile
mvn clean compile

# Test
mvn test

# Package
mvn package

# Run
java -jar target/task-manager-1.0.0.jar
# OR
mvn exec:java -Dexec.mainClass="com.taskmanager.Main"
```

### Code Quality

| Aspect | Implementation |
|--------|----------------|
| **Immutability** | Domain objects use immutable design with copy methods |
| **Validation** | Input validation at multiple layers |
| **Error Handling** | Custom exceptions with meaningful messages |
| **Documentation** | Comprehensive JavaDoc comments |
| **Naming** | Descriptive, intention-revealing names |
| **DRY** | No code duplication |

## 📚 Documentation

### Key Highlights

<table>
<tr>
<td width="33%">

**Architecture**
- Clean layered design
- Clear separation of concerns
- Dependency inversion
- Interface-based contracts

</td>
<td width="33%">

**Best Practices**
- SOLID principles
- Design patterns
- Immutable objects
- Comprehensive testing

</td>
<td width="33%">

**Modern Java**
- Java 25 LTS features
- Records & sealed types
- Enhanced switch
- Stream API mastery

</td>
</tr>
</table>

### Learning Outcomes

This project demonstrates proficiency in:

✅ Modern Java 25 LTS development  
✅ Clean architecture and layered design  
✅ Design pattern implementation (7 patterns)  
✅ SOLID principles application (all 5)  
✅ Unit testing with JUnit 5  
✅ Build automation with Maven  
✅ Version control (50 incremental commits)  
✅ Professional documentation  

## 📈 Project Stats

| Metric | Value |
|--------|-------|
| **Lines of Code** | ~2,000+ |
| **Java Files** | 19 source + 3 test |
| **Commits** | 50 (Sept 1 - Oct 20, 2025) |
| **Dependencies** | Minimal (Gson, JUnit 5) |
| **Test Coverage** | 31 tests, 100% pass |
| **Java Version** | 25.0.1 LTS |

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

<div align="center">

**Built with ❤️ using Java 25 LTS**

[![GitHub](https://img.shields.io/badge/GitHub-waqas1412-181717?style=flat-square&logo=github)](https://github.com/waqas1412)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-0077B5?style=flat-square&logo=linkedin)](https://linkedin.com/in/waqas1412)

⭐ Star this repo if you find it helpful!

</div>

