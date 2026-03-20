
/**
 * Represents a course identified by its course code.
 * Each course maintains its own roster of enrolled students.
 */
package seedu.address.model.course;

import java.util.ArrayList;
import java.util.Objects;

import seedu.address.model.student.Student;

/**
 * Represents a course in the address book.
 */
public class Course {

    public static final String MESSAGE_CONSTRAINTS = "Course code does not follow the correct format!";

    private final String courseCode;
    private final ArrayList<Student> students;

    /**
     * Constructs a Course with the specified course code.
     *
     * @param courseCode the course code
     */
    public Course(String courseCode) {
        this.courseCode = courseCode.trim().toUpperCase();
        this.students = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Returns all students enrolled in this course.
     *
     * @return ArrayList of students
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Returns true if a student with the given studentId is enrolled in this
     * course.
     */
    public boolean hasStudent(String studentId) {
        return students.stream().anyMatch(s -> s.getStudentId().equalsIgnoreCase(studentId));
    }

    /** Adds a student to this course's roster. */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Removes the student with the given studentId from the roster.
     *
     * @return true if the student was found and removed, false otherwise.
     */
    public boolean removeStudent(String studentId) {
        return students.removeIf(s -> s.getStudentId().equalsIgnoreCase(studentId));
    }

    /**
     * Returns true if the given course has the same identity as this course.
     *
     * @param otherCourse the other course to compare
     * @return true if same course, false otherwise
     */
    public boolean isSameCourse(Course otherCourse) {
        if (otherCourse == this) {
            return true;
        }
        return otherCourse != null && otherCourse.getCourseCode().equals(getCourseCode());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Course && courseCode.equals(((Course) other).courseCode));
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

    @Override
    public String toString() {
        return "[" + courseCode + "]";
    }
}
