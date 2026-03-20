package seedu.address.model.assessment.exceptions;

/**
 * Signals that the operation is unable to find the specified Assessment.
 */
public class AssessmentNotFoundException extends RuntimeException {
    public AssessmentNotFoundException() {
        super("Assessment not found.");
    }
}
