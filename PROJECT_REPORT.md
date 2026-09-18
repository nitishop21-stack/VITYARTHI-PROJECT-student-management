# PROJECT REPORT: STUDENT MANAGEMENT SYSTEM

---

## 1. Executive Summary / Abstract
The **Student Management System** is a standalone terminal application engineered in Java that demonstrates core and intermediate Object-Oriented Programming (OOP) principles. It provides an efficient platform for educational institutions and administrators to maintain student enrollment records, manage contact details, log semester-wise course grades, compute Cumulative Grade Point Averages (CGPA) on a 10-point scale, and evaluate final academic standings. Designed with zero external library dependencies, the system emphasizes high reliability, input sanitization, encapsulation, and modular design.

---

## 2. Introduction & Problem Statement
In educational environments, managing student credentials, personal information, and academic performance manually or across disparate spreadsheets is prone to data entry errors, accidental record overwrites, and inconsistent GPA calculations. 

The primary objective of this project is to construct a console-driven management software in Java that offers:
- Controlled data mutation via object-oriented encapsulation.
- Dynamic storage of records without rigid array bounds using Java Collections (`ArrayList`).
- Intuitive and crash-proof command-line navigation.
- Automated, standardized performance metrics (CGPA & letter grading).

---

## 3. Core Concepts & Object-Oriented Design

The system implements the following fundamental software engineering and Java concepts:

### 3.1 Classes and Objects
- Real-world entities are abstracted into domain models (`Person`, `Student`).
- State and behavior are tightly unified within each class.

### 3.2 Abstraction
- The `Person` abstract class models high-level attributes (`name`, `email`, `contactNumber`) and enforces a contractual obligation via the abstract method `public abstract void displayDetails()`. Subclasses must provide concrete representations for their specific domains.

### 3.3 Inheritance
- The `Student` class extends `Person`, inheriting personal profile attributes while augmenting them with academic metrics (`studentId`, `course`, `semester`, `subjectMarks`, `cgpa`, and `grade`). This reduces code duplication and models a natural "is-a" relationship.

### 3.4 Polymorphism
- The `displayDetails()` method declared in `Person` is overridden in `Student` to render a structured, customized student profile card. When treated through polymorphic references, the runtime environment invokes the appropriate overridden method.

### 3.5 Encapsulation
- All member fields in `Person` and `Student` are strictly declared `private`. Access is granted exclusively through validated getters and setters. Mutators enforce invariants (e.g., marks constrained strictly between $0.0$ and $100.0$, non-null strings).

### 3.6 Collections Framework (`ArrayList` & `LinkedHashMap`)
- `ArrayList<Student>` in `StudentManager` dynamically scales as records are enrolled or removed, avoiding the fixed-size limitations of primitive arrays.
- `LinkedHashMap<String, Double>` preserves the insertion order of subjects while providing fast $O(1)$ key lookups for subject-level score updates.

### 3.7 Control Flow & Error Resilience
- Menu dispatch is handled using structured `switch` constructs and `while` loops.
- Terminal inputs are filtered through `InputValidator`, shielding the system from common scanner buffer issues, improper data types, and invalid numeric bounds.

---

## 4. Class & Module Architecture

```
                                +-------------------+
                                |   <<abstract>>    |
                                |      Person       |
                                +-------------------+
                                | - name: String    |
                                | - email: String   |
                                | - contact: String |
                                +-------------------+
                                | + displayDetails()|
                                +---------^---------+
                                          |
                                          | extends
                                          |
                                +---------+---------+
                                |      Student      |
                                +-------------------+
                                | - studentId       |
                                | - course          |
                                | - semester        |
                                | - subjectMarks    |
                                | - cgpa, grade     |
                                +-------------------+
                                | + computeCGPA()   |
                                | + displayDetails()|
                                +-------------------+

+-----------------------+              |
|    InputValidator     |              | holds
+-----------------------+              |
| + readString()        |              v
| + readInt()           |    +--------------------+
| + readDouble()        |    |   StudentManager   |
| + readEmail()         |    +--------------------+
| + readPhoneNumber()   |    | - students: List   |
+-----------------------+    +--------------------+
                             | + addStudent()     |
                             | + deleteStudent()  |
                             | + findStudentById()|
                             | + displayAll()     |
                             +--------------------+
                                       ^
                                       | uses
                             +---------+----------+
                             |        Main        |
                             +--------------------+
                             | + main()           |
                             | + printMainMenu()  |
                             +--------------------+
```

### Module Descriptions:
1. **`model.Person`**:
   - Fields: `name`, `email`, `contactNumber`.
   - Responsibilities: Base profile storage, encapsulation, abstract contract.
2. **`model.Student`**:
   - Fields: `studentId`, `course`, `semester`, `subjectMarks`, `cgpa`, `grade`.
   - Responsibilities: Academic record maintenance, grade calculation, profile display formatting.
3. **`service.StudentManager`**:
   - Fields: `List<Student> students`.
   - Responsibilities: ArrayList CRUD management, unique ID enforcement, search queries (ID and name substrings), sample data seeding.
4. **`util.InputValidator`**:
   - Responsibilities: Safe Scanner consumption, regex validation for email and phone numbers, numeric range enforcement, line buffering.
5. **`Main`**:
   - Responsibilities: CLI rendering, menu loop orchestration, and user workflow delegation.

---

## 5. Algorithmic Formulations

### 5.1 Cumulative Grade Point Average (CGPA)
The system adopts the standard university 10-point scale:

$$\text{Average Percentage } (P) = \frac{\sum_{i=1}^{N} \text{Mark}_i}{N}$$

$$\text{CGPA} = \frac{P}{10.0} \quad (\text{rounded to 2 decimal places})$$

### 5.2 Letter Grade Classification Matrix

| Percentage Range | Grade Scale | Classification | Pass / Fail Status |
|---|---|---|---|
| $90.0\% \le P \le 100.0\%$ | 9.00 – 10.00 | **A+** (Outstanding) | Pass |
| $80.0\% \le P < 90.0\%$  | 8.00 – 8.99  | **A** (Excellent)   | Pass |
| $70.0\% \le P < 80.0\%$  | 7.00 – 7.99  | **B+** (Very Good)  | Pass |
| $60.0\% \le P < 70.0\%$  | 6.00 – 6.99  | **B** (Good)        | Pass |
| $50.0\% \le P < 60.0\%$  | 5.00 – 5.99  | **C** (Average)     | Pass |
| $40.0\% \le P < 50.0\%$  | 4.00 – 4.99  | **D** (Pass)        | Pass |
| $P < 40.0\%$             | < 4.00       | **F** (Fail)        | Fail |

---

## 6. Functional Verification & Test Scenarios

The system underwent rigorous verification across all functional units:

| Test ID | Test Scenario | Input Data | Expected Output | Status |
|---|---|---|---|---|
| **TC-01** | Add Student with Valid Details | ID: `STU105`, Name: `Rajesh Kumar`, Course: `Data Science`, Sem: `1`, Marks: `95.0, 91.0` | Student enrolled, CGPA calculated as `9.30`, Grade `A+` | **PASSED** |
| **TC-02** | Add Student with Duplicate ID | ID: `STU101` (already present) | Error prompt: ID exists, re-prompt for unique ID | **PASSED** |
| **TC-03** | Search Student by Exact ID | ID: `STU101` | Full profile card with subjects, marks, CGPA | **PASSED** |
| **TC-04** | Search Student by Partial Name | Name: `diya` | Displays Diya Patel record irrespective of case | **PASSED** |
| **TC-05** | Update Student Details | Change name to `Diya P. Sharma`, add subject `Advanced Algorithms (95)` | Details and marks updated, CGPA recalculated to `8.40` | **PASSED** |
| **TC-06** | Delete Student with Confirmation | ID: `STU103`, Confirmation: `y` | Student removed; count decremented in display table | **PASSED** |
| **TC-07** | Display All Students | Table generation | Formatted ASCII table with headers, columns, and total count | **PASSED** |
| **TC-08** | Input Type Validation | Text entered when integer expected | Informative error displayed, prompt repeats without crash | **PASSED** |
| **TC-09** | Out-of-bounds Mark Entry | Mark: `105.0` or `-5.0` | Validation error: must be between 0.0 and 100.0 | **PASSED** |

---

## 7. Build and Execution Instructions

The project compiles with zero third-party dependencies:

```bash
# Compilation
javac -d bin src/model/*.java src/service/*.java src/util/*.java src/Main.java

# Execution
java -cp bin Main
```

---

## 8. Conclusion
The **Student Management System** successfully fulfills all academic and functional requirements. By adhering to core Object-Oriented principles (Encapsulation, Inheritance, Polymorphism, Abstraction) and pairing them with robust input handling and dynamic collections, the application delivers a resilient, clean, and extensible platform for terminal-based academic management.
