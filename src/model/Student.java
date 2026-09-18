package model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a Student in the system.
 * Inherits common attributes from Person, demonstrating Inheritance and Polymorphism.
 */
public class Student extends Person {
    private String studentId;
    private String course;
    private int semester;
    private final Map<String, Double> subjectMarks;
    private double cgpa;
    private String grade;

    public Student(String studentId, String name, String email, String contactNumber, String course, int semester) {
        super(name, email, contactNumber);
        this.studentId = studentId;
        this.course = course;
        this.semester = semester;
        this.subjectMarks = new LinkedHashMap<>();
        this.cgpa = 0.0;
        this.grade = "N/A";
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Map<String, Double> getSubjectMarks() {
        return Collections.unmodifiableMap(subjectMarks);
    }

    public void addOrUpdateMark(String subject, double mark) {
        if (mark < 0 || mark > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100.");
        }
        this.subjectMarks.put(subject, mark);
        recalculateGradesAndCGPA();
    }

    public void clearMarks() {
        this.subjectMarks.clear();
        this.cgpa = 0.0;
        this.grade = "N/A";
    }

    public double getCgpa() {
        return cgpa;
    }

    public String getGrade() {
        return grade;
    }

    /**
     * Calculates the 10-point CGPA and corresponding letter grade
     * based on recorded subject scores out of 100.
     */
    public void recalculateGradesAndCGPA() {
        if (subjectMarks.isEmpty()) {
            this.cgpa = 0.0;
            this.grade = "N/A";
            return;
        }

        double totalMarks = 0.0;
        for (double mark : subjectMarks.values()) {
            totalMarks += mark;
        }

        double averagePercentage = totalMarks / subjectMarks.size();
        
        // Standard 10-point CGPA scaling: percentage / 10.0, rounded to 2 decimals
        this.cgpa = Math.round((averagePercentage / 10.0) * 100.0) / 100.0;

        // Grade mapping based on percentage and passing criteria
        if (averagePercentage >= 90.0) {
            this.grade = "A+ (Outstanding)";
        } else if (averagePercentage >= 80.0) {
            this.grade = "A (Excellent)";
        } else if (averagePercentage >= 70.0) {
            this.grade = "B+ (Very Good)";
        } else if (averagePercentage >= 60.0) {
            this.grade = "B (Good)";
        } else if (averagePercentage >= 50.0) {
            this.grade = "C (Average)";
        } else if (averagePercentage >= 40.0) {
            this.grade = "D (Pass)";
        } else {
            this.grade = "F (Fail)";
        }
    }

    /**
     * Polymorphic implementation of displayDetails.
     * Displays a comprehensive card of the student's profile.
     */
    @Override
    public void displayDetails() {
        System.out.println("------------------------------------------------------------");
        System.out.println(" Student Profile: " + getName() + " [" + studentId + "]");
        System.out.println("------------------------------------------------------------");
        System.out.printf("  Course / Major : %s\n", course);
        System.out.printf("  Semester       : %d\n", semester);
        System.out.printf("  Email Address  : %s\n", getEmail());
        System.out.printf("  Contact Number : %s\n", getContactNumber());
        System.out.printf("  CGPA (10-point): %.2f\n", cgpa);
        System.out.printf("  Overall Grade  : %s\n", grade);

        if (!subjectMarks.isEmpty()) {
            System.out.println("  Subjects & Marks (out of 100):");
            for (Map.Entry<String, Double> entry : subjectMarks.entrySet()) {
                System.out.printf("    - %-20s : %.2f\n", entry.getKey(), entry.getValue());
            }
        } else {
            System.out.println("  Subjects & Marks : No marks recorded yet.");
        }
        System.out.println("------------------------------------------------------------");
    }
}
