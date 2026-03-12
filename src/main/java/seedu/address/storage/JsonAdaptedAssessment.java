package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.MaxScore;

public class JsonAdaptedAssessment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Assessment's %s field is missing!";

    private final String assessmentName;
    private final String maxScore;

    @JsonCreator
    public JsonAdaptedAssessment(@JsonProperty("assessmentName") String assessmentName,
            @JsonProperty("maxScore") String maxScore) {
        this.assessmentName = assessmentName;
        this.maxScore = maxScore;
    }

    public JsonAdaptedAssessment(Assessment source) {
        assessmentName = source.getAssessmentName().toString();
        maxScore = source.getMaxScore().toString();
    }

    public Assessment toModelType() throws IllegalValueException {
        if (assessmentName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "assessmentName"));
        }
        if (assessmentName.trim().isEmpty()) {
            throw new IllegalValueException(AssessmentName.MESSAGE_CONSTRAINTS);
        }

        if (maxScore == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "maxScore"));
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

        return new Assessment(modelAssessmentName, modelMaxScore);
    }
}
