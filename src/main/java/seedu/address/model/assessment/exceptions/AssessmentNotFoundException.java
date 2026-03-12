package seedu.address.model.assessment.exceptions;

public class AssessmentNotFoundException extends RuntimeException {
    public AssessmentNotFoundException() {
        super("Assessment not found.");
    }
}
