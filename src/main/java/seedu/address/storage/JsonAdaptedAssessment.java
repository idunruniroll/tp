package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.MaxScore;

public class JsonAdaptedAssessment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Assessment's %s field is missing!";

    private final String courseCode;
    private final String assessmentName;
    private final String maxScore;

    @JsonCreator
    public JsonAdaptedAssessment(@JsonProperty("courseCode") String courseCode,
            @JsonProperty("assessmentName") String assessmentName,
            @JsonProperty("maxScore") String maxScore) {
        this.courseCode = courseCode;
        this.assessmentName = assessmentName;
        this.maxScore = maxScore;
    }

    public JsonAdaptedAssessment(Assessment source) {
        courseCode = source.getCourseCode();
        assessmentName = source.getAssessmentName().toString();
        maxScore = source.getMaxScore().toString();
    }

    public Assessment toModelType() throws IllegalValueException {
        if (courseCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "courseCode"));
        }
        if (courseCode.trim().isEmpty()) {
            throw new IllegalValueException("Course code cannot be empty.");
        }

        if (assessmentName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "assessmentName"));
        }
        if (assessmentName.trim().isEmpty()) {
            throw new IllegalValueException(AssessmentName.MESSAGE_CONSTRAINTS);
        }

        if (maxScore == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "maxScore"));
        }

        final AssessmentName modelAssessmentName;
        final MaxScore modelMaxScore;

        try {
            modelAssessmentName = new AssessmentName(assessmentName);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(AssessmentName.MESSAGE_CONSTRAINTS);
        }

        try {
            modelMaxScore = new MaxScore(maxScore);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(MaxScore.MESSAGE_CONSTRAINTS);
        }

        return new Assessment(courseCode, modelAssessmentName, modelMaxScore);
    }
}
