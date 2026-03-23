package seedu.address.model.assessment;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public class Assessment {
    private final String courseCode;
    private final AssessmentName assessmentName;
    private final MaxScore maxScore;

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

    public MaxScore getMaxScore() {
        return maxScore;
    }

    public boolean isSameAssessment(Assessment otherAssessment) {
        if (otherAssessment == this) {
            return true;
        }
        return otherAssessment != null
                && courseCode.equalsIgnoreCase(otherAssessment.courseCode)
                && assessmentName.equals(otherAssessment.assessmentName);
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
