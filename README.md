# Student Management System (Java CLI) ⭐

An Object-Oriented, command-line Student Management System written in standard Java. This application provides comprehensive student record management, subject score tracking, and automated 10-point CGPA and letter grade computation.

---

## 📋 Table of Contents
1. [Project Overview](#project-overview)
2. [Key Features](#key-features)
3. [Object-Oriented Architecture](#object-oriented-architecture)
4. [Prerequisites & Environment Setup](#prerequisites--environment-setup)
5. [Step-by-Step Compilation & Execution](#step-by-step-compilation--execution)
   - [Windows (PowerShell / Command Prompt)](#windows-powershell--cmd)
   - [macOS / Linux (Terminal)](#macos--linux-terminal)
6. [CLI Navigation & Feature Guide](#cli-navigation--feature-guide)
7. [Grading & CGPA Calculation Logic](#grading--cgpa-calculation-logic)
8. [Project Structure](#project-structure)
9. [Troubleshooting](#troubleshooting)

---

## 📖 Project Overview
The **Student Management System** is a standalone terminal application engineered to streamline student academic administration. Built without any third-party dependencies, it demonstrates foundational and advanced Java programming concepts:
- **Classes & Objects**: Real-world modeling of academic profiles.
- **Inheritance & Polymorphism**: Abstract base entity extended with specialized behaviors.
- **Encapsulation**: Private state protection with input validation.
- **Dynamic Collections (`ArrayList`)**: In-memory data store for student entities.
- **Robust CLI Input Parsing**: Zero-crash input scanner with automated recovery.

---

## ✨ Key Features

1. **Add Student**: Register a new student with unique ID, full name, email, contact number, course/branch, semester, and optional subject marks.
2. **Delete Student**: Remove a student record by ID with an explicit confirmation safeguard.
3. **Search Student**:
   - By Unique Student ID (exact, case-insensitive).
   - By Name (partial substring matching, case-insensitive).
4. **Update Student Details**: Modular updates for individual attributes (Name, Email, Contact, Course, Semester) and subject mark additions/revisions.
5. **Display All Students**: Formatted ASCII table summarizing all enrolled students, their CGPA, standing, and contact information.
6. **Calculate Grades & CGPA**: Computes subject-wise letter grades, cumulative 10-point CGPA, overall percentage, and academic standing on demand.
7. **Pre-Loaded Sample Data**: Ships with 4 realistic student records pre-loaded so evaluators can inspect and verify features immediately upon launching.

---

## 🏗️ Object-Oriented Architecture

The project adheres to clean OOP principles:

- **`model.Person` (Abstract Base Class)**:
  - Encapsulates common human attributes (`name`, `email`, `contactNumber`).
  - Defines an abstract contract `public abstract void displayDetails()`.
- **`model.Student` (Derived Entity)**:
  - Extends `Person` (Inheritance).
  - Encapsulates student-specific properties: `studentId`, `course`, `semester`, `subjectMarks` (`Map<String, Double>`), `cgpa`, and `grade`.
  - Implements polymorphic `displayDetails()` for profile cards.
  - Automatically maintains recalculated CGPA and letter grade whenever marks change.
- **`service.StudentManager` (Business Service Layer)**:
  - Uses `java.util.ArrayList<Student>` for dynamic collection management.
  - Implements CRUD operations, searching filters, and dataset seeding.
- **`util.InputValidator` (Utility Layer)**:
  - Handles console input validation, preventing `InputMismatchException`, scanner buffer leftover newlines, and unhandled exceptions.
- **`Main` (Presentation / Driver Layer)**:
  - Runs the interactive CLI loop.

---

## ⚙️ Prerequisites & Environment Setup

### 1. Java Development Kit (JDK)
- **Required**: Java SE Development Kit (JDK) **8 or higher** (JDK 11, 17, 21, or 26 recommended).
- **Check Java Installation**:
  Open your terminal or command prompt and run:
  ```bash
  javac -version
  java -version
  ```
  If both commands display version numbers (e.g., `javac 17.x.x` or `javac 26.x.x`), you are ready to proceed.

### 2. JDK Installation (if not installed)
- **Windows**: Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [Eclipse Temurin](https://adoptium.net/). Ensure you check "Add to PATH" during installation.
- **macOS** (Homebrew):
  ```bash
  brew install openjdk
  ```
- **Linux (Ubuntu/Debian)**:
  ```bash
  sudo apt update && sudo apt install default-jdk
  ```

---

## 🚀 Step-by-Step Compilation & Execution

No build tools (Maven/Gradle) or internet connections are required. The project compiles into standard Java bytecode using `javac`.

### Windows (PowerShell / CMD)

1. Open **Command Prompt** or **PowerShell**.
2. Navigate to the project root directory:
   ```cmd
   cd "C:\Users\abhir\OneDrive\Desktop\JAVA Vityarathi"
   ```
3. Create the output directory (if not already created):
   ```cmd
   if not exist bin mkdir bin
   ```
4. Compile all Java source files into the `bin` directory:
   ```cmd
   javac -d bin src/model/*.java src/service/*.java src/util/*.java src/Main.java
   ```
5. Execute the compiled application:
   ```cmd
   java -cp bin Main
   ```

---

### macOS / Linux (Terminal)

1. Open your terminal.
2. Navigate to the project root directory:
   ```bash
   cd "/path/to/JAVA Vityarathi"
   ```
3. Create the binary output directory:
   ```bash
   mkdir -p bin
   ```
4. Compile all Java source files:
   ```bash
   javac -d bin src/model/*.java src/service/*.java src/util/*.java src/Main.java
   ```
5. Run the application:
   ```bash
   java -cp bin Main
   ```

---

## 🖥️ CLI Navigation & Feature Guide

Upon launching, you are greeted with the interactive menu:

```text
============================================================
          WELCOME TO STUDENT MANAGEMENT SYSTEM              
============================================================
[*] Pre-loaded 4 sample student records for testing.

------------------------------------------------------------
                      MAIN MENU                             
------------------------------------------------------------
  1. Add Student
  2. Delete Student
  3. Search Student (by ID or Name)
  4. Update Student Details
  5. Display All Students
  6. Calculate & View Student Grades / CGPA
  7. Reset / Reload Sample Data
  8. Exit Application
------------------------------------------------------------
Enter your choice (1-8): 
```

### Quick Walkthrough of Menu Actions:
- **Option 1 (Add Student)**: Prompts for ID, full name, email (validated format), phone number (7-15 digits), course, semester (1-12), and subject marks.
- **Option 2 (Delete Student)**: Prompts for Student ID and asks for confirmation (`y/n`) before removal.
- **Option 3 (Search Student)**: Allows searching by exact Student ID or by case-insensitive name keyword.
- **Option 4 (Update Student Details)**: Opens a dedicated sub-menu allowing granular updates to name, email, contact, course, semester, or subject scores.
- **Option 5 (Display All Students)**: Prints an ASCII tabular overview of all enrolled students with their current CGPA, letter grade, and contact.
- **Option 6 (Calculate & View Grades/CGPA)**: Displays a comprehensive academic scorecard with subject breakdown, total marks, percentage, 10-point CGPA, and letter standing.
- **Option 7 (Reset / Reload Sample Data)**: Reloads default sample records.
- **Option 8 (Exit)**: Exits the terminal program safely.

---

## 📊 Grading & CGPA Calculation Logic

The grading engine implements standard university 10-point CGPA evaluation:

### 1. Cumulative Grade Point Average (CGPA)
Given $N$ subjects with individual percentage scores $S_i \in [0, 100]$:
$$\text{Average Percentage} = \frac{\sum_{i=1}^{N} S_i}{N}$$
$$\text{CGPA} = \frac{\text{Average Percentage}}{10.0} \quad (\text{rounded to 2 decimal places})$$

### 2. Letter Grade Scale

| Percentage Range | 10-Point Scale | Letter Grade | Academic Standing |
|:----------------:|:--------------:|:------------:|:-----------------:|
| 90.0% – 100.0%   | 9.00 – 10.00   | **A+**       | Outstanding       |
| 80.0% – 89.9%    | 8.00 – 8.99    | **A**        | Excellent         |
| 70.0% – 79.9%    | 7.00 – 7.99    | **B+**       | Very Good         |
| 60.0% – 69.9%    | 6.00 – 6.99    | **B**        | Good              |
| 50.0% – 59.9%    | 5.00 – 5.99    | **C**        | Average           |
| 40.0% – 49.9%    | 4.00 – 4.99    | **D**        | Pass              |
| Below 40.0%      | Below 4.00     | **F**        | Fail              |

---

## 📁 Project Structure

```
JAVA Vityarathi/
├── bin/                       # Compiled .class files (generated upon compilation)
├── src/
│   ├── Main.java              # CLI entry point and menu router
│   ├── model/
│   │   ├── Person.java        # Abstract base class (Abstraction & Encapsulation)
│   │   └── Student.java       # Entity class (Inheritance, Polymorphism, Grading)
│   ├── service/
│   │   └── StudentManager.java# Business logic layer (ArrayList CRUD & Search)
│   └── util/
│       └── InputValidator.java# CLI scanner wrapper with input error recovery
├── README.md                  # Comprehensive setup and run documentation
└── PROJECT_REPORT.md          # Formal project report
```

---

## 🔧 Troubleshooting

- **`javac: command not found` / `'javac' is not recognized`**:
  Ensure the JDK `bin` directory (e.g., `C:\Program Files\Java\jdk-xx\bin`) is added to your system's `PATH` environment variable. Restart the terminal after updating environment variables.
- **`ClassNotFoundException: Main`**:
  Ensure you run the `java` command with the classpath flag pointing to `bin`:
  ```bash
  java -cp bin Main
  ```
- **Accidental Non-Numeric Input in Terminal**:
  The system includes an `InputValidator` that traps formatting mistakes gracefully and re-prompts without terminating or crashing.
