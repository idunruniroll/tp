/**
 * Represents a course.
 * @author zow1e
 */
package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import java.util.Objects;

public class Course {
    private final String courseCode;

    // Placeholder fields - implement when needed
    private final java.util.List<String> students;
    private final java.util.List<String> assessments;
    private final java.util.List<String> grades;

    public Course(String courseCode) {
        requireAllNonNull(courseCode);
        this.courseCode = courseCode;
        this.students = new java.util.ArrayList<>();
        this.assessments = new java.util.ArrayList<>();
        this.grades = new java.util.ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    // Placeholder getters - return empty for now
    public java.util.List<String> getStudents() {
        return new java.util.ArrayList<>(students);
    }

    public java.util.List<String> getAssessments() {
        return new java.util.ArrayList<>(assessments);
    }

    public java.util.List<String> getGrades() {
        return new java.util.ArrayList<>(grades);
    }

    public boolean isSameCourse(Course otherCourse) {
        if (otherCourse == this) {
            return true;
        }
        return otherCourse != null
            && otherCourse.getCourseCode().equals(getCourseCode());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Course)) {
            return false;
        }
        Course otherCourse = (Course) other;
        return courseCode.equals(otherCourse.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

    @Override
    public String toString() {
        return "[" + getCourseCode() + "]";
    }
}
