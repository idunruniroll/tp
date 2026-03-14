package seedu.address.model.assessment.exceptions;

public class DuplicateAssessmentException extends RuntimeException {
    public DuplicateAssessmentException() {
        super("Operation would result in duplicate assessments.");
    }
}
