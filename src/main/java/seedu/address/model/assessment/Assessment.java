package seedu.address.model.assessment;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public class Assessment {
    private final AssessmentName assessmentName;
    private final MaxScore maxScore;

    public Assessment(AssessmentName assessmentName, MaxScore maxScore) {
        requireNonNull(assessmentName);
        requireNonNull(maxScore);
        this.assessmentName = assessmentName;
        this.maxScore = maxScore;
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
                && otherAssessment.getAssessmentName().equals(getAssessmentName());
    }

    @Override
    public String toString() {
        return assessmentName + " / " + maxScore;
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
        return assessmentName.equals(otherAssessment.assessmentName)
                && maxScore.equals(otherAssessment.maxScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessmentName, maxScore);
    }
}
