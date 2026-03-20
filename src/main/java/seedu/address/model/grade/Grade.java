package seedu.address.model.grade;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.student.StudentId;

/**
 * Represents a grade for a student on an assessment.
 */
public class Grade {
    private final String courseCode;
    private final StudentId studentId;
    private final AssessmentName assessmentName;
    private final Score score;

    /**
     * Constructs a Grade with all required fields.
     *
     * @param courseCode the course code
     * @param studentId the student ID
     * @param assessmentName the assessment name
     * @param score the score
     */
    public Grade(String courseCode, StudentId studentId, AssessmentName assessmentName, Score score) {
        requireNonNull(courseCode);
        requireNonNull(studentId);
        requireNonNull(assessmentName);
        requireNonNull(score);
        this.courseCode = courseCode;
        this.studentId = studentId;
        this.assessmentName = assessmentName;
        this.score = score;
    }

    /**
     * Constructs a Grade with minimal fields.
     *
     * @param studentId the student ID
     * @param assessmentName the assessment name
     * @param courseCode the course code
     */
    public Grade(StudentId studentId, AssessmentName assessmentName, String courseCode) {
        requireNonNull(studentId);
        requireNonNull(assessmentName);
        requireNonNull(courseCode);
        this.studentId = studentId;
        this.assessmentName = assessmentName;
        this.courseCode = courseCode;
        this.score = null;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public AssessmentName getAssessmentName() {
        return assessmentName;
    }

    /**
     * Gets the score for this grade.
     *
     * @return the score
     */
    public Score getScore() {
        return score;
    }

    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Returns true if the given grade has the same identity as this grade.
     *
     * @param otherGrade the other grade to compare
     * @return true if same grade, false otherwise
     */
    public boolean isSameGrade(Grade otherGrade) {
        if (otherGrade == this) {
            return true;
        }
        return otherGrade != null
                && courseCode.equalsIgnoreCase(otherGrade.courseCode)
                && studentId.equals(otherGrade.studentId)
                && assessmentName.equals(otherGrade.assessmentName);
    }

    @Override
    public String toString() {
        return studentId + ", Assessment Name: " + assessmentName + " , Grade: " + score;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Grade)) {
            return false;
        }
        Grade otherGrade = (Grade) other;
        return courseCode.equals(otherGrade.courseCode)
                && studentId.equals(otherGrade.studentId)
                && assessmentName.equals(otherGrade.assessmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode, studentId, assessmentName);
    }
}
