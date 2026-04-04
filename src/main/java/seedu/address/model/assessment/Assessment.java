package seedu.address.model.assessment;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents an assessment for a course.
 */
public class Assessment {
    private final String courseCode;
    private final AssessmentName assessmentName;
    private final MaxScore maxScore;

    /**
     * Constructs an Assessment with the given course code, assessment name, and max
     * score.
     * @param courseCode     the course code
     * @param assessmentName the assessment name
     * @param maxScore       the maximum score
     */
    public Assessment(String courseCode, AssessmentName assessmentName, MaxScore maxScore) {
        requireNonNull(courseCode);
        requireNonNull(assessmentName);
        requireNonNull(maxScore);
        this.courseCode = courseCode.trim().toUpperCase();
        this.assessmentName = assessmentName;
        this.maxScore = maxScore;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public AssessmentName getAssessmentName() {
        return assessmentName;
    }

    /**
     * Returns the maximum score for this assessment.
     * @return the maximum score
     */
    public MaxScore getMaxScore() {
        return maxScore;
    }

    /**
     * Returns true if the given assessment has the same identity as this
     * assessment.
     * @param otherAssessment the other assessment to compare
     * @return true if same assessment, false otherwise
     */
    public boolean isSameAssessment(Assessment other) {
        if (other == this) {
            return true;
        }

        return other != null
                && courseCode.equalsIgnoreCase(other.courseCode)
                && assessmentName.getNormalizedName()
                        .equals(other.assessmentName.getNormalizedName());
    }

    @Override
    public String toString() {
        return "Assessment Name: " + assessmentName + " (Max Score: " + maxScore + ") in " + courseCode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Assessment)) {
            return false;
        }
        Assessment otherAssessment = (Assessment) other;
        return courseCode.equals(otherAssessment.courseCode)
                && assessmentName.equals(otherAssessment.assessmentName)
                && maxScore.equals(otherAssessment.maxScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode, assessmentName, maxScore);
    }
}
