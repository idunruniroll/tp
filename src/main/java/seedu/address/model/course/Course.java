/**
 * Represents a course.
 * @author zow1e
 */
package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Objects;

public class Course {
    private final String courseCode;
    private final ArrayList<Student> students;
    private final ArrayList<Assessment> assessments;
    private final ArrayList<Grade> grades;

    // Placeholder fields - implement when needed
    private final java.util.List<String> students;
    private final java.util.List<String> assessments;
    private final java.util.List<String> grades;

    public Course(String courseCode) {
        this.courseCode = courseCode;
        this.students = new ArrayList<>();
        this.assessments = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Assessment> getAssessments() {
        return assessments;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
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
