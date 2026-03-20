package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Student enrolled in a course.
 * Identity is determined solely by studentId (case-insensitive).
 */
public class Student {

    private final String studentId;
    private final String studentName;
    private final Optional<String> email;

    /**
     * Constructs a Student with student ID and name, without email.
     */
    public Student(String studentId, String studentName) {
        requireAllNonNull(studentId, studentName);
        this.studentId = studentId.trim().toUpperCase();
        this.studentName = studentName.trim();
        this.email = Optional.empty();
    }

    /**
     * Constructs a Student with student ID, name, and email.
     */
    public Student(String studentId, String studentName, String email) {
        requireAllNonNull(studentId, studentName, email);
        this.studentId = studentId.trim().toUpperCase();
        this.studentName = studentName.trim();
        this.email = Optional.of(email.trim());
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    /**
     * Returns the email of the student.
     */
    public Optional<String> getEmail() {
        return email;
    }

    /**
     * Returns true if the given student has the same identity as this student.
     *
     * @param otherStudent the other student to compare
     * @return true if same student, false otherwise
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }
        return otherStudent != null
                && otherStudent.getStudentId().equalsIgnoreCase(getStudentId());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Student)) {
            return false;
        }
        Student otherStudent = (Student) other;
        return studentId.equalsIgnoreCase(otherStudent.studentId)
                && studentName.equals(otherStudent.studentName)
                && email.equals(otherStudent.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId.toUpperCase(), studentName, email);
    }

    @Override
    public String toString() {
        String base = studentId + " " + studentName;
        return email.map(e -> base + " (" + e + ")").orElse(base);
    }
}
