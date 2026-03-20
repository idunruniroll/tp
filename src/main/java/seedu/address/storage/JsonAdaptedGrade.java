package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.grade.Grade;
import seedu.address.model.grade.Score;
import seedu.address.model.student.StudentId;

/**
 * Jackson-friendly version of {@link Grade}.
 */
public class JsonAdaptedGrade {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Grade's %s field is missing!";

    private final String courseCode; // Add courseCode
    private final String studentId;
    private final String assessmentName;
    private final String score;

    /**
     * Constructs a {@code JsonAdaptedGrade} with the given grade details.
     */
    @JsonCreator
    public JsonAdaptedGrade(@JsonProperty("courseCode") String courseCode,
            @JsonProperty("studentId") String studentId,
            @JsonProperty("assessmentName") String assessmentName,
            @JsonProperty("score") String score) {
        this.courseCode = courseCode;
        this.studentId = studentId;
        this.assessmentName = assessmentName;
        this.score = score;
    }

    /**
     * Constructs a {@code JsonAdaptedGrade} using the given {@link Grade}.
     */
    public JsonAdaptedGrade(Grade source) {
        courseCode = source.getCourseCode(); // Ensure courseCode is passed
        studentId = source.getStudentId().toString();
        assessmentName = source.getAssessmentName().toString();
        score = source.getScore().toString();
    }

    /**
     * Converts this Jackson-friendly adapted grade object into a {@link Grade} object.
     *
     * @return a new Grade object
     * @throws IllegalValueException if there were any data constraints violated
     */
    public Grade toModelType() throws IllegalValueException {
        if (courseCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "courseCode"));
        }
        if (courseCode.trim().isEmpty()) {
            throw new IllegalValueException("CourseCode cannot be empty.");
        }

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

        return new Grade(courseCode, modelStudentId, modelAssessmentName, modelScore); // constructor
    }
}
