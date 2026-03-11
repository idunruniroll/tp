package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.ArrayList;

import seedu.address.model.assessment.Assessment;
import seedu.address.model.grade.Grade;
import seedu.address.model.student.Student;

public class Course {
    private final static ArrayList<Course> courseCodeList;
    private final static ArrayList<Student> students;
    private final static ArrayList<Assessment> assessments;
    private final static ArrayList<Grade> grades;
    private final String courseCode;

    public Course(String courseCode) {
        requireAllNonNull(courseCode);
        this.courseCode = courseCode;
        this.students = Student.getStudentsList(); // function to be created; students that exist in this course
        this.assessments = Student.getAssessmentsList(); // function to be created; assessments that exist in this course
        this.grades = Student.getGradesList(); // function to be created ; grades of students that exist in this course
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
