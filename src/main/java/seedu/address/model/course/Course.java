

/**
 * Represents a Course in the system.
 * Each course maintains its own roster of enrolled students.
/**
 * Represents a course.
 * @author zow1e
 */
package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.student.Student;
import java.util.Objects;
import java.util.ArrayList;

/**
 * Represents a course identified by its course code.
 */
public class Course {

    private final String courseCode;
    private final ArrayList<Student> students;

    public Course(String courseCode) {
        this.courseCode = courseCode.trim().toUpperCase();
        this.students = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    /** Returns true if a student with the given studentId is enrolled in this course. */
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