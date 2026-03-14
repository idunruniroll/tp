package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.grade.Grade;
import seedu.address.model.grade.Score;
import seedu.address.model.student.StudentId;

public class JsonAdaptedGrade {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Grade's %s field is missing!";

    private final String studentId;
    private final String assessmentName;
    private final String score;

    @JsonCreator
    public JsonAdaptedGrade(@JsonProperty("studentId") String studentId,
            @JsonProperty("assessmentName") String assessmentName,
            @JsonProperty("score") String score) {
        this.studentId = studentId;
        this.assessmentName = assessmentName;
        this.score = score;
    }

    public JsonAdaptedGrade(Grade source) {
        studentId = source.getStudentId().toString();
        assessmentName = source.getAssessmentName().toString();
        score = source.getScore().toString();
    }

    public Grade toModelType() throws IllegalValueException {
        if (studentId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "studentId"));
        }
        if (studentId.trim().isEmpty()) {
            throw new IllegalValueException("StudentId cannot be empty.");
        }

        if (assessmentName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "assessmentName"));
        }
        if (assessmentName.trim().isEmpty()) {
            throw new IllegalValueException(AssessmentName.MESSAGE_CONSTRAINTS);
        }

        if (score == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "score"));
        }

        final StudentId modelStudentId;
        final AssessmentName modelAssessmentName;
        final Score modelScore;

        try {
            modelStudentId = new StudentId(studentId);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Invalid studentId.");
        }

        try {
            modelAssessmentName = new AssessmentName(assessmentName);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(AssessmentName.MESSAGE_CONSTRAINTS);
        }

        try {
            modelScore = new Score(score);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Score.MESSAGE_CONSTRAINTS);
        }

        return new Grade(modelStudentId, modelAssessmentName, modelScore);
    }
}
