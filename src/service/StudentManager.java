package service;

import model.Student;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class managing student records using an ArrayList.
 * Implements business operations: Add, Delete, Search, Update, Display, and Grade Calculation.
 */
public class StudentManager {
    private final List<Student> students;

    public StudentManager() {
        this.students = new ArrayList<>();
    }

    /**
     * Adds a new student to the system.
     * Prevents duplicate student IDs.
     *
     * @param student The student object to add.
     * @return true if added successfully, false if ID already exists.
     */
    public boolean addStudent(Student student) {
        if (student == null) {
            return false;
        }
        if (findStudentById(student.getStudentId()) != null) {
            return false;
        }
        students.add(student);
        return true;
    }

    /**
     * Deletes a student by their unique student ID.
     *
     * @param studentId The ID of the student to delete.
     * @return true if found and removed, false otherwise.
     */
    public boolean deleteStudent(String studentId) {
        Student target = findStudentById(studentId);
        if (target != null) {
            return students.remove(target);
        }
        return false;
    }

    /**
     * Searches for a student by exact ID (case-insensitive).
     *
     * @param studentId The student ID to search for.
     * @return The Student object if found, or null.
     */
    public Student findStudentById(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return null;
        }
        for (Student student : students) {
            if (student.getStudentId().equalsIgnoreCase(studentId.trim())) {
                return student;
            }
        }
        return null;
    }

    /**
     * Searches for students whose names contain the given keyword.
     *
     * @param keyword Partial or full name.
     * @return List of matching students.
     */
    public List<Student> searchStudentsByName(String keyword) {
        List<Student> results = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return results;
        }
        String lowerKeyword = keyword.trim().toLowerCase();
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(lowerKeyword)) {
                results.add(student);
            }
        }
        return results;
    }

    /**
     * Returns an unmodifiable copy of all students.
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Returns total count of enrolled students.
     */
    public int getStudentCount() {
        return students.size();
    }

    /**
     * Displays all students in a neatly formatted ASCII table.
     */
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("\n[!] No student records found. Add a new student or load sample data.");
            return;
        }

        System.out.println("\n==========================================================================================================");
        System.out.println("                                          ENROLLED STUDENTS LIST                                          ");
        System.out.println("==========================================================================================================");
        System.out.printf("%-10s | %-20s | %-18s | %-8s | %-8s | %-15s | %-12s\n",
                "ID", "Name", "Course", "Semester", "CGPA", "Grade", "Contact");
        System.out.println("----------------------------------------------------------------------------------------------------------");

        for (Student student : students) {
            System.out.printf("%-10s | %-20s | %-18s | %-8d | %-8.2f | %-15s | %-12s\n",
                    student.getStudentId(),
                    truncate(student.getName(), 20),
                    truncate(student.getCourse(), 18),
                    student.getSemester(),
                    student.getCgpa(),
                    student.getGrade(),
                    truncate(student.getContactNumber(), 12));
        }
        System.out.println("==========================================================================================================");
        System.out.printf("Total Registered Students: %d\n\n", students.size());
    }

    /**
     * Populates realistic sample data for instant demonstration and grading.
     */
    public void seedSampleData() {
        if (!students.isEmpty()) {
            return;
        }

        Student s1 = new Student("STU101", "Aarav Sharma", "aarav.sharma@uni.edu", "9876543210", "Computer Science", 4);
        s1.addOrUpdateMark("Data Structures", 92.5);
        s1.addOrUpdateMark("Object Oriented Java", 88.0);
        s1.addOrUpdateMark("Database Systems", 94.0);
        s1.addOrUpdateMark("Computer Networks", 86.5);
        addStudent(s1);

        Student s2 = new Student("STU102", "Diya Patel", "diya.patel@uni.edu", "9812345678", "Information Tech", 3);
        s2.addOrUpdateMark("Discrete Mathematics", 78.0);
        s2.addOrUpdateMark("Operating Systems", 82.5);
        s2.addOrUpdateMark("Web Development", 85.0);
        s2.addOrUpdateMark("Software Engineering", 79.5);
        addStudent(s2);

        Student s3 = new Student("STU103", "Rohan Mehta", "rohan.mehta@uni.edu", "9765432109", "Electrical Engg", 2);
        s3.addOrUpdateMark("Circuit Theory", 65.0);
        s3.addOrUpdateMark("Digital Electronics", 72.0);
        s3.addOrUpdateMark("Calculus II", 58.5);
        s3.addOrUpdateMark("Physics Lab", 68.0);
        addStudent(s3);

        Student s4 = new Student("STU104", "Ananya Verma", "ananya.v@uni.edu", "9123456780", "Computer Science", 6);
        s4.addOrUpdateMark("Machine Learning", 96.0);
        s4.addOrUpdateMark("Cloud Computing", 91.0);
        s4.addOrUpdateMark("Artificial Intelligence", 95.5);
        s4.addOrUpdateMark("Distributed Systems", 89.0);
        addStudent(s4);
    }

    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 2) + "..";
    }
}
