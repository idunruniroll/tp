package seedu.address.model.course;

import java.util.ArrayList;
import java.util.Objects;

import seedu.address.model.assessment.Assessment;
import seedu.address.model.grade.Grade;
import seedu.address.model.student.Student;

public class Course {
    private final String courseCode;
    private final ArrayList<Student> students;
    private final ArrayList<Assessment> assessments;
    private final ArrayList<Grade> grades;

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
        return courseCode.equals(otherCourse.courseCode)
                && students.equals(otherCourse.students)
                && assessments.equals(otherCourse.assessments)
                && grades.equals(otherCourse.grades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode, students, assessments, grades);
    }
}
