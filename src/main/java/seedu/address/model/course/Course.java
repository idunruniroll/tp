/**
 * Represents a course.
 *
 * @author zow1e
 */

package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

// import seedu.address.model.assessment.UniqueAssessmentList;
// import seedu.address.model.grade.UniqueGradeList;
// import seedu.address.model.student.UniqueStudentList;

public class Course {
    private final String courseCode;
    // private final UniqueStudentList students;
    // private final UniqueAssessmentList assessments;
    // private final UniqueGradeList grades;

    public Course(String courseCode) {
        requireAllNonNull(courseCode);
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public UniqueStudentList getStudents() {
        return students;
    }

    public UniqueAssessmentList getAssessments() {
        return assessments;
    }

    public UniqueGradeList getGrades() {
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

    // @Override
    // public int hashCode() {
    //     return Objects.hash(courseCode, students, assessments, grades);
    // }

    /**
     * Returns a string representation of this course for display.
     *
     * @return formatted display string of the course
     */
    @Override
    public String toString() {
        String fullDesc = "[" + getCourseCode() + "]";
        return fullDesc;
    }
}
