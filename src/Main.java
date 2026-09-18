import model.Student;
import service.StudentManager;
import util.InputValidator;

import java.util.List;
import java.util.Map;

/**
 * Main application entry point for the Student Management System.
 * Provides an interactive terminal-based CLI menu.
 */
public class Main {
    private static final StudentManager manager = new StudentManager();

    public static void main(String[] args) {
        // Automatically pre-populate sample data for immediate evaluation convenience
        manager.seedSampleData();

        boolean running = true;
        System.out.println("============================================================");
        System.out.println("          WELCOME TO STUDENT MANAGEMENT SYSTEM              ");
        System.out.println("============================================================");
        System.out.println("[*] Pre-loaded 4 sample student records for testing.");

        while (running) {
            printMainMenu();
            int choice = InputValidator.readInt("Enter your choice (1-8): ", 1, 8);

            switch (choice) {
                case 1:
                    handleAddStudent();
                    break;
                case 2:
                    handleDeleteStudent();
                    break;
                case 3:
                    handleSearchStudent();
                    break;
                case 4:
                    handleUpdateStudent();
                    break;
                case 5:
                    handleDisplayAllStudents();
                    break;
                case 6:
                    handleCalculateGradesAndCGPA();
                    break;
                case 7:
                    handleLoadSampleData();
                    break;
                case 8:
                    running = false;
                    System.out.println("\nThank you for using the Student Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n------------------------------------------------------------");
        System.out.println("                      MAIN MENU                             ");
        System.out.println("------------------------------------------------------------");
        System.out.println("  1. Add Student");
        System.out.println("  2. Delete Student");
        System.out.println("  3. Search Student (by ID or Name)");
        System.out.println("  4. Update Student Details");
        System.out.println("  5. Display All Students");
        System.out.println("  6. Calculate & View Student Grades / CGPA");
        System.out.println("  7. Reset / Reload Sample Data");
        System.out.println("  8. Exit Application");
        System.out.println("------------------------------------------------------------");
    }

    private static void handleAddStudent() {
        System.out.println("\n--- [1] ADD NEW STUDENT ---");
        String studentId;
        while (true) {
            studentId = InputValidator.readString("Enter Student ID (e.g. STU105): ");
            if (manager.findStudentById(studentId) != null) {
                System.out.printf("  [!] Error: Student ID '%s' already exists. Please use a unique ID.\n", studentId);
            } else {
                break;
            }
        }

        String name = InputValidator.readString("Enter Full Name: ");
        String email = InputValidator.readEmail("Enter Email Address: ");
        String phone = InputValidator.readPhoneNumber("Enter Contact Phone Number: ");
        String course = InputValidator.readString("Enter Course / Branch (e.g., Computer Science): ");
        int semester = InputValidator.readInt("Enter Current Semester (1-12): ", 1, 12);

        Student student = new Student(studentId, name, email, phone, course, semester);

        boolean addMarks = InputValidator.readConfirmation("Do you want to add subject marks now?");
        if (addMarks) {
            int count = InputValidator.readInt("How many subjects do you want to enter (1-10)? ", 1, 10);
            for (int i = 1; i <= count; i++) {
                System.out.printf(" Subject %d:\n", i);
                String subjectName = InputValidator.readString("   Enter Subject Name: ");
                double mark = InputValidator.readDouble("   Enter Marks (0.0 to 100.0): ", 0.0, 100.0);
                student.addOrUpdateMark(subjectName, mark);
            }
        }

        if (manager.addStudent(student)) {
            System.out.printf("\n[SUCCESS] Student '%s' (%s) has been successfully enrolled!\n", student.getName(), student.getStudentId());
        } else {
            System.out.println("\n[ERROR] Failed to enroll student.");
        }
    }

    private static void handleDeleteStudent() {
        System.out.println("\n--- [2] DELETE STUDENT ---");
        if (manager.getStudentCount() == 0) {
            System.out.println("[!] No students in the system to delete.");
            return;
        }

        String id = InputValidator.readString("Enter Student ID to delete: ");
        Student student = manager.findStudentById(id);
        if (student == null) {
            System.out.printf("[!] No student found with ID: %s\n", id);
            return;
        }

        System.out.printf("Found Student: %s (%s) - %s\n", student.getName(), student.getStudentId(), student.getCourse());
        boolean confirm = InputValidator.readConfirmation("Are you sure you want to permanently delete this student record?");
        if (confirm) {
            manager.deleteStudent(id);
            System.out.printf("[SUCCESS] Student '%s' with ID %s has been deleted.\n", student.getName(), id);
        } else {
            System.out.println("[CANCELLED] Deletion cancelled by user.");
        }
    }

    private static void handleSearchStudent() {
        System.out.println("\n--- [3] SEARCH STUDENT ---");
        System.out.println("  1. Search by Student ID");
        System.out.println("  2. Search by Student Name");
        int subChoice = InputValidator.readInt("Choose search mode (1-2): ", 1, 2);

        if (subChoice == 1) {
            String id = InputValidator.readString("Enter Student ID: ");
            Student student = manager.findStudentById(id);
            if (student != null) {
                System.out.println("\n[Match Found]");
                student.displayDetails();
            } else {
                System.out.printf("[!] No student found with ID: %s\n", id);
            }
        } else {
            String keyword = InputValidator.readString("Enter Name (or part of name) to search: ");
            List<Student> results = manager.searchStudentsByName(keyword);
            if (results.isEmpty()) {
                System.out.printf("[!] No student found matching name: '%s'\n", keyword);
            } else {
                System.out.printf("\n[Matches Found: %d]\n", results.size());
                for (Student student : results) {
                    student.displayDetails();
                }
            }
        }
    }

    private static void handleUpdateStudent() {
        System.out.println("\n--- [4] UPDATE STUDENT DETAILS ---");
        String id = InputValidator.readString("Enter Student ID to update: ");
        Student student = manager.findStudentById(id);
        if (student == null) {
            System.out.printf("[!] No student found with ID: %s\n", id);
            return;
        }

        boolean updating = true;
        while (updating) {
            System.out.println("\n------------------------------------------------------------");
            System.out.printf("  Editing Student: %s (%s)\n", student.getName(), student.getStudentId());
            System.out.println("------------------------------------------------------------");
            System.out.println("  1. Update Name (Current: " + student.getName() + ")");
            System.out.println("  2. Update Email (Current: " + student.getEmail() + ")");
            System.out.println("  3. Update Contact Number (Current: " + student.getContactNumber() + ")");
            System.out.println("  4. Update Course / Major (Current: " + student.getCourse() + ")");
            System.out.println("  5. Update Semester (Current: " + student.getSemester() + ")");
            System.out.println("  6. Manage Subject Marks (Add/Update/Clear)");
            System.out.println("  7. Done / Return to Main Menu");
            System.out.println("------------------------------------------------------------");

            int choice = InputValidator.readInt("Select field to update (1-7): ", 1, 7);
            switch (choice) {
                case 1:
                    String newName = InputValidator.readString("Enter new full name: ");
                    student.setName(newName);
                    System.out.println("[SUCCESS] Name updated.");
                    break;
                case 2:
                    String newEmail = InputValidator.readEmail("Enter new email: ");
                    student.setEmail(newEmail);
                    System.out.println("[SUCCESS] Email updated.");
                    break;
                case 3:
                    String newPhone = InputValidator.readPhoneNumber("Enter new contact number: ");
                    student.setContactNumber(newPhone);
                    System.out.println("[SUCCESS] Contact number updated.");
                    break;
                case 4:
                    String newCourse = InputValidator.readString("Enter new course/major: ");
                    student.setCourse(newCourse);
                    System.out.println("[SUCCESS] Course updated.");
                    break;
                case 5:
                    int newSem = InputValidator.readInt("Enter new semester (1-12): ", 1, 12);
                    student.setSemester(newSem);
                    System.out.println("[SUCCESS] Semester updated.");
                    break;
                case 6:
                    manageStudentMarks(student);
                    break;
                case 7:
                    updating = false;
                    System.out.println("[SUCCESS] Student updates saved.");
                    break;
            }
        }
    }

    private static void manageStudentMarks(Student student) {
        boolean managing = true;
        while (managing) {
            System.out.println("\n  --- Manage Marks for " + student.getName() + " ---");
            System.out.println("  1. Add or Update a Subject Mark");
            System.out.println("  2. Clear All Marks");
            System.out.println("  3. Return to Edit Menu");

            int opt = InputValidator.readInt("  Select option (1-3): ", 1, 3);
            if (opt == 1) {
                String subject = InputValidator.readString("  Enter Subject Name: ");
                double mark = InputValidator.readDouble("  Enter Marks (0.0 to 100.0): ", 0.0, 100.0);
                student.addOrUpdateMark(subject, mark);
                System.out.printf("  [SUCCESS] Mark for '%s' recorded as %.2f. CGPA updated to %.2f (%s).\n",
                        subject, mark, student.getCgpa(), student.getGrade());
            } else if (opt == 2) {
                boolean confirm = InputValidator.readConfirmation("  Are you sure you want to clear all subject marks?");
                if (confirm) {
                    student.clearMarks();
                    System.out.println("  [SUCCESS] All marks cleared.");
                }
            } else if (opt == 3) {
                managing = false;
            }
        }
    }

    private static void handleDisplayAllStudents() {
        manager.displayAllStudents();
    }

    private static void handleCalculateGradesAndCGPA() {
        System.out.println("\n--- [6] CALCULATE GRADES & CGPA ---");
        String id = InputValidator.readString("Enter Student ID: ");
        Student student = manager.findStudentById(id);
        if (student == null) {
            System.out.printf("[!] No student found with ID: %s\n", id);
            return;
        }

        System.out.println("\n============================================================");
        System.out.printf(" Academic Performance Report: %s (%s)\n", student.getName(), student.getStudentId());
        System.out.println("============================================================");

        Map<String, Double> marks = student.getSubjectMarks();
        if (marks.isEmpty()) {
            System.out.println(" [!] No subject marks recorded for this student.");
            boolean addNow = InputValidator.readConfirmation(" Would you like to enter subject marks now?");
            if (addNow) {
                int count = InputValidator.readInt(" How many subjects to enter (1-10)? ", 1, 10);
                for (int i = 1; i <= count; i++) {
                    System.out.printf(" Subject %d:\n", i);
                    String sub = InputValidator.readString("   Enter Subject Name: ");
                    double score = InputValidator.readDouble("   Enter Marks (0.0 - 100.0): ", 0.0, 100.0);
                    student.addOrUpdateMark(sub, score);
                }
            } else {
                return;
            }
        }

        // Display breakdown
        System.out.println("\n Subject-wise Breakdown:");
        System.out.println(" -----------------------------------------------------------");
        System.out.printf(" %-25s | %-12s | %-15s\n", "Subject", "Score (/100)", "Subject Grade");
        System.out.println(" -----------------------------------------------------------");

        double total = 0;
        for (Map.Entry<String, Double> entry : student.getSubjectMarks().entrySet()) {
            double score = entry.getValue();
            total += score;
            String subjectGrade;
            if (score >= 90) subjectGrade = "A+ (Outstanding)";
            else if (score >= 80) subjectGrade = "A (Excellent)";
            else if (score >= 70) subjectGrade = "B+ (Very Good)";
            else if (score >= 60) subjectGrade = "B (Good)";
            else if (score >= 50) subjectGrade = "C (Average)";
            else if (score >= 40) subjectGrade = "D (Pass)";
            else subjectGrade = "F (Fail)";

            System.out.printf(" %-25s | %-12.2f | %-15s\n", entry.getKey(), score, subjectGrade);
        }

        int numSubjects = student.getSubjectMarks().size();
        double avg = total / numSubjects;
        System.out.println(" -----------------------------------------------------------");
        System.out.printf(" Total Subjects : %d\n", numSubjects);
        System.out.printf(" Total Marks    : %.2f / %.2f\n", total, (numSubjects * 100.0));
        System.out.printf(" Percentage     : %.2f%%\n", avg);
        System.out.printf(" Cumulative GPA : %.2f / 10.0\n", student.getCgpa());
        System.out.printf(" Final Standing : %s\n", student.getGrade());
        System.out.println("============================================================");
    }

    private static void handleLoadSampleData() {
        System.out.println("\n--- [7] LOAD SAMPLE DATA ---");
        if (manager.getStudentCount() > 0) {
            boolean confirm = InputValidator.readConfirmation("Existing records found. Do you want to reload sample data? (Existing records will remain)");
            if (!confirm) {
                return;
            }
        }
        manager.seedSampleData();
        System.out.printf("[SUCCESS] Sample data active. Total records in system: %d\n", manager.getStudentCount());
    }
}
